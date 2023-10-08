package com.courseservice.Soapwebcourse.soap;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//Enable WebService
@EnableWs
//Spring Configuration
@Configuration
public class WebServiceConfig {

	//MessageDispatcherServlet ==> acts as A router to define which controller handel request
	//Application Context
	//url -> /ws/*
	@Bean   //help us to map servlet to uri
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherServlet , "/ws/*") ; 
	}
	// use schema to generate the wsdl
	/* We Need to define
	1- PortType - Course Port
	2- NameSpace - http://in28minutes.com/courses
	* */

	// /ws/course.wsdl
	// courses-details.xsd

	@Bean(name = "courses")
	public DefaultWsdl11Definition defaultWsdl11Definition( XsdSchema courseSchema){
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("CoursePort");
		definition.setTargetNamespace("http://in28minutes.com/courses");
		definition.setLocationUri("/ws");
		definition.setSchema(courseSchema);

		return definition;


	}

	@Bean
	public XsdSchema courseSchema(){
		return new SimpleXsdSchema(new ClassPathResource("courses-details.xsd"));
	}
}
