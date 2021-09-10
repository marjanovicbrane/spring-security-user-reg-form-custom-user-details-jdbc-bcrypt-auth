package com.brane.security.user.reg.form.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//THIS IS JAVA CONFIG CLASS.WE ARE USING PURE JAVA CONFIG, NO XML
@Configuration
//with this annotation we enable Spring MVC
@EnableWebMvc
//so we can do hibernate transaction in the background(@Transactional)
@EnableTransactionManagement
//base package where to scan components for Controller class,Service class,Reporistory class...
@ComponentScan("com.brane.security.user.reg.form")
//Classpath is a parameter in the Java Virtual Machine or the Java compiler 
//that specifies the location of user-defined classes and packages. 
//The parameter may be set either on the command-line, or through an environment variable.
@PropertySource("classpath:persistence-mysql.properties")
//with this interface WebMvcConfigurer we can make our custom configuration for SPRING MVC
//all methods are default methods, which are not implemented in this class
//Evrey class which have this annotation @EnableWebMvc can implement this interface WebMvcConfigurer
public class DemoAppConfig implements WebMvcConfigurer {

	//this variable env holds properties from the persistence-mysql.properties file
	//THIS IS GLOBAL ENVIRONMENT VARIABLE
	//Annotation @PropertySource READS DATA FROM THE PROPERTIES FILE AND 
	//WE ARE GOING TO INJECT THAT DATA INTO VARIABLE env
	//ENVIRONMENT INTERFACE REPRESENTS THE ENVIRONMENT IN WHICH THE CURRENT APPLICATION IS LAUNCHED 
	//we are going to inject Environment object so we can get properties from the files 
	//persistence-mysql.properties and security-persistence-mysql.properties
	@Autowired
	private Environment env;
	
	//we are going to set logger just for diagnostics
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	//define a bean for ViewResolver, because we are using JSP pages
	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		//set prefix
		viewResolver.setPrefix("/WEB-INF/view/");
		
		//set suffix
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	
	//DataSource for database spring_security_custom_user_demo
	//we created a method which returns DataSource object
	//in this method we set all our props for our spring_security_custom_user_demo
	@Bean
	public DataSource securityDataSource() {
		
		// create connection pool, this class is from c3p0 package
		ComboPooledDataSource securityDataSource= new ComboPooledDataSource();
				
		// set the jdbc driver
		try {
			securityDataSource.setDriverClass(env.getProperty("com.mysql.jdbc.Driver"));
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		
		//let's log url and user ... just to make sure we are reading the data
		logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info("jdbc.user=" + env.getProperty("jdbc.user"));
		
		
		// set database connection props
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));
		
		// set connection pool props
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		return securityDataSource;
	}
	
	
	
	//need a helper method, read environment property and convert to int
	private int getIntProperty(String propName) {
		
		String propVal = env.getProperty(propName);
		
		// now convert to int
		int intPropVal = Integer.parseInt(propVal);
		
		return intPropVal;
	}
	
	

	
	//this method contains all what we have in myDataSource() method
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		
		//create session factory
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		// set the properties
		//this sessionFactory object now contains all what we have in securityDataSource() method
		sessionFactory.setDataSource(securityDataSource());
		
		//we are going to set package for all our entity classes which need to be scaned (com.luv2code.springdemo.entity)
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		
		//we are calling method for hibernate dialect and hibernate show sql
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}
	
	
	//this method will load  2 props and this method we calling in sessionFactory() method
	private Properties getHibernateProperties() {

		// set hibernate properties
		Properties props = new Properties();

		//set property for hibernate.dialect and hibernate.show_sql
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		
		return props;				
	}
		

	
	@Bean
	@Autowired
	//WE ARE GOING TO DO HERE DEPENDENCY INJECTION, BECAUSE OF THAT WE CREATE ABOVE SPRING BEAN OBJECTS
	//WE WANT TO HAVE ALL WHAT WE CREATED HERE IN OUR METHOD transactionManager()
	//ALL WHAT WE CREATED FOR NOW WE HAVE IN METHOD sessionFactory() AND NOW WE INJECT THAT BEAN ID
	//IN OUR transactionManager() METHOD LIKE ARGUMENT
		
	//THIS METHOD WILL USE US FOR TRANSACTION,EVERY CHANGE ON DB IS TRANSACTION(CREATE,UPDATE,DELETE)
	//THIS METHOD WILL TAKE CARE OF @TRANSACTIONAL ANNOTATION, SO WE DON'T NEED TO
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}		
}
