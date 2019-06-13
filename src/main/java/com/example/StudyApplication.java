package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class StudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	}
/*
	@Bean
	public ServletRegistrationBean testServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WxCodeServlet());
		registration.addUrlMappings("/hello");
		return registration;
	}

	@Bean
	public ServletRegistrationBean wxOpenIdServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WxOpenIdServlet());
		registration.addUrlMappings("/wxOpenId");
		return registration;
	}
	*/
}
