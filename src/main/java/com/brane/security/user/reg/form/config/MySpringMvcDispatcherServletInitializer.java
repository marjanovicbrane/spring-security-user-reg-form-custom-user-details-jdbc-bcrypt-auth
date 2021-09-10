package com.brane.security.user.reg.form.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

	//THIS IS JAVA CONFIG CLASS.WE ARE USING PURE JAVA CONFIG, NO XML
	//This config class Servlet Initializer, allows us starting the APPLICATION CONTEXT (DemoAppConfig.class)
	//and also starting root application context which we don't have now.

	//OUR CONFIG CODE IN CLASS DemoAppConfig.class IS AUTOMATICALLY DETECTED AND IT IS USING FOR INITIALIZATION
	//SERVLET OBJECT WHICH IS STORED IN SPRING CONTAINER.WE ARE USING THIS METHOD getServletConfigClasses() FOR THAT.
	public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


		//WE DON'T HAVE ROOT CONFIG CLASS, ONLY SERVLET CONFIG CLASS
		@Override
		protected Class<?>[] getRootConfigClasses() {
			// TODO Auto-generated method stub
			return null;
		}
		
		//BECAUSE OF THIS METHOD, WE AUTOMATICALLY DETECTED OUR CONFIG CLASS DemoAppConfig.class. APPLICATION CONTEXT
		//AND THIS METHOD IS USING FOR INITIALIZATION SERVLET OBJECT WHICH IS STORED IN SPRING CONTAINER.
		@Override
		protected Class<?>[] getServletConfigClasses() {

			return new Class[] { DemoAppConfig.class };
		}


		
		//SERVLET MAPPING-pattern "/" root of application
		@Override
		protected String[] getServletMappings() {

			return new String[] { "/" };
		}

}






