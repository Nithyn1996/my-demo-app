package com.common.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.common.api.constant.APIFixedConstant;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource({ "classpath:application.properties" })
public class SwaggerConfig extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${swagger.doc.title}")
	private String swaggerDocTitle = "";
	@Value("${swagger.doc.description}")
	private String swaggerDocDescription = "";
	@Value("${swagger.doc.version}")
	private String swaggerDocVersion = "";
	@Value("${swagger.doc.termsOfServiceUrl}")
	private String swaggerDocTermsOfServiceUrl = "";
	@Value("${swagger.doc.license}")
	private String swaggerDocLicense = "";
	@Value("${swagger.doc.licenseUrl}")
	private String swaggerDocLicenseUrl = "";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors
					.basePackage(GC_RSOURCE_PACKAGE_NAME))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(swaggerDocTitle).description(swaggerDocDescription).version(swaggerDocVersion).termsOfServiceUrl(swaggerDocTermsOfServiceUrl).license(swaggerDocLicense)
				.licenseUrl(swaggerDocLicenseUrl).build();
	}

}