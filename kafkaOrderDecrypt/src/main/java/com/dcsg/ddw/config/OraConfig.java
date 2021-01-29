package com.dcsg.ddw.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OraConfig {
	
	 @Value("${spring.datasource.url}")
	  private String url;

	  @Value("${spring.datasource.username}")
	  private String user;

	  @Value("${spring.datasource.password}")
	  private String pwd;

	  @Value("${spring.datasource.driver-class-name}")
	  private String driver;

	/*
	@Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
          .driverClassName("oracle.jdbc.driver.OracleDriver")
          .url("jdbc:oracle:thin:@dkha3121:1521:ecomu")
          .username("SB_LOAD")
          .password("SbLoad2020")
          .build(); 
    }
    */
		@Bean
	    public DataSource datasource() {
	        return DataSourceBuilder.create()
	          .driverClassName(driver)
	          .url(url)
	          .username(user)
	          .password(pwd)
	          .build(); 
		}
	 	  
	  

}
