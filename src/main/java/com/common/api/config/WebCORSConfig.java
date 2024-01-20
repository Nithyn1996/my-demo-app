package com.common.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class WebCORSConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
				.allowedHeaders("Accept", "content-type", "Authorization", "X-Requested-With",
								"x-request-appCode", "x-request-userName", "x-request-sessionId",
								"x-request-ipAddress", "sessionId", "companyId", "referredBy")
				.exposedHeaders("content-type", "Date")
				.allowCredentials(true).maxAge(3600);

	}

}
