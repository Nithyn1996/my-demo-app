package com.common.api.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.util.IOUtils;
import com.common.api.util.APILog;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

@Configuration
@PropertySource({ "classpath:application.properties" })
public class AzureStorageProvider {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${app.azure.storage.account.name}")
	private String appAzureStorageAccountName = "";
	@Value("${app.azure.storage.api.account.key}")
	private String appAzureStorageAccountKey = "";

	/** Properties Constants */
	@Value("${app.file.storage.access.mode}")
	private boolean appFileStorageAccessMode = false;
	@Value("${app.file.storage.container.name}")
	private String appFileStorageContainerName = "";

	public boolean uploadMultipartFile(String folderName, String subFolderName, String fileName, MultipartFile file) {

		String apiLogString = "LOG_PROVIDER_MULTIPART_FILE_UPLOAD: ";

		if (appFileStorageAccessMode) {

			String containerName = appFileStorageContainerName;
 			if (subFolderName.length() > 0) {
 				folderName = folderName + "/" + subFolderName;
 			}
 			String blobName      = folderName + "/" + fileName;

 			// Define the connection-string with your values
 			String storageConnectionString =
 			    "DefaultEndpointsProtocol=https;" +
 			    "AccountName=" + appAzureStorageAccountName + ";" +
 			    "AccountKey=" + appAzureStorageAccountKey;

	 		try {

	 			// Retrieve storage account from connection-string.
	 			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

	 			// Create the blob client.
	 			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

	 		   // Retrieve reference to a previously created container.
	 			CloudBlobContainer container = blobClient.getContainerReference(containerName);
	 		    // Define the path to a local file.

	 			// Create or overwrite the "myimage.jpg" blob with contents from a local file.
	 			CloudBlockBlob blob = container.getBlockBlobReference(blobName);
	 			blob.upload(file.getInputStream(), file.getSize());

			    return true;

	 		} catch (AmazonServiceException errMess) {
				APILog.writeTraceLog(apiLogString + "AmazonServiceException : " + errMess.getMessage());
	        } catch (AmazonClientException errMess) {
	        	APILog.writeTraceLog(apiLogString + "AmazonClientException : " + errMess.getMessage());
	        } catch (IOException errMess) {
	        	APILog.writeTraceLog(apiLogString + "IOException : " + errMess.getMessage());
 			} catch (Exception errMess) {
				APILog.writeTraceLog(apiLogString + " Exception : " + errMess.getMessage());
			}
		} else {
			APILog.writeInfoLog(apiLogString + " appAWSS3AccessMode: " + appFileStorageAccessMode);
		}
 		return false;
 	}

	public byte[] downloadMultipartFile(String folderName, String subFolderName, String fileName) {

		byte[] bytes  = {};
		String apiLogString = "LOG_PROVIDER_MULTIPART_FILE_DOWNLOAD: ";

 		if (appFileStorageAccessMode) {

 			String containerName = appFileStorageContainerName;
 			if (subFolderName.length() > 0) {
 				folderName = folderName + "/" + subFolderName;
 			}
 			String blobName      = folderName + "/" + fileName;

 			// Define the connection-string with your values
 			String storageConnectionString =
 			    "DefaultEndpointsProtocol=https;" +
 			    "AccountName=" + appAzureStorageAccountName + ";" +
 			    "AccountKey=" + appAzureStorageAccountKey;

 			try {

			    // Retrieve storage account from connection-string.
				CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

 				// Create the blob client.
 				CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

			    // Get a reference to a container.
			    // The container name must be lower case
 				CloudBlobContainer container = blobClient.getContainerReference(containerName);

 				// I expected to get all files thanks to the next row
 				Iterable<ListBlobItem> blobs = container.listBlobs(folderName, true); // HERE THE CHANGE

 				// No blob found this time
 				for (ListBlobItem blob : blobs) { // NEVER IN THE FOR
 				    //log the current blob URI
 				    if (blob instanceof CloudBlob) {
 				        CloudBlob cloudBlob = (CloudBlob) blob;
 				        //make nice things with every found file
 				        if (cloudBlob.getName().equalsIgnoreCase(blobName)) {
					        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					        cloudBlob.download(outputStream);
					        InputStream result1 = new ByteArrayInputStream(outputStream.toByteArray());
					        bytes = IOUtils.toByteArray(result1);
					        return bytes;
			    	   	}
 				    }
 				}

	        } catch (AmazonServiceException errMess) {
	        	APILog.writeTraceLog(apiLogString + " AmazonServiceException : " + errMess.getMessage());
	        } catch (AmazonClientException errMess) {
	        	APILog.writeTraceLog(apiLogString + " AmazonClientException : " + errMess.getMessage());
	        } catch (IOException errMess) {
	        	APILog.writeTraceLog(apiLogString + " IOException : " + errMess.getMessage());
			} catch (Exception errMess) {
				APILog.writeTraceLog(apiLogString + " Exception : " + errMess.getMessage());
			}

		} else {
			APILog.writeInfoLog(apiLogString + " appAWSS3AccessMode: " + appFileStorageAccessMode);
		}
		return bytes;
	}

}
