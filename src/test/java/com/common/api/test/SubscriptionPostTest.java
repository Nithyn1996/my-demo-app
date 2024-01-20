package com.common.api.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.common.api.APICommonApp;
import com.common.api.model.Subscription;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = APICommonApp.class)
@AutoConfigureMockMvc
public class SubscriptionPostTest {

    @Autowired
    private MockMvc mockMvc;

    // All values are valid
    @Test
    public void validValues() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }


	// Invalid companyId
    @Test
    public void invalidCompanyId() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp000000000000000000011");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid divisionId
    @Test
    public void invalidDivisionId() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup000000000000000000011");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid groupId
    @Test
    public void invalidGroupId() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup000000000000000000011");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid Status
    @Test
    public void invalidStatus() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTEREDs");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid type
    @Test
    public void invalidType() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTIONs");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid invalidName
    @Test
    public void invalidName() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription @1");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid referredBy
    @Test
    public void invalidReferredBy() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROIDs")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid sessionId
    @Test
    public void invalidSession() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874bs")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Session Fail
    @Test
    public void SessionFail() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "202120212021202120212021")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(440));
    }

	// Access Fail
    @Test
    public void AccessFail() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 20");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "6501b212b1d5ae00a4790901")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

	// Name Already Exist
    @Test
    public void nameAlreadyExist() throws Exception {

    	Subscription subscription = new Subscription();
    			subscription.setCompanyId("comp00000000000000000001");
    			subscription.setGroupId("grup00000000000000000001");
    			subscription.setDivisionId("divi00000000000000000001");
    			subscription.setName("My Mobile Device License Subscription 1");
    			subscription.setIosLicenseCount(20);
    			subscription.setAndroidLicenseCount(10);
    			subscription.setStatus("REGISTERED");
    			subscription.setType("MOBILE_DEVICE_LICENSE_SUBSCRIPTION");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .content(new ObjectMapper().writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(409));
    }
}
