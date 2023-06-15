package com.assignment.se.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
				.allowedOrigins("*") // 모든 출처 허용
				.allowedMethods("*") // 모든 HTTP 메서드 허용
				.allowCredentials(false); // 자격증명 허용 여부
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resource/**")
				.addResourceLocations("classpath:/article")
				.addResourceLocations("classpath:/lecture");

	}
}