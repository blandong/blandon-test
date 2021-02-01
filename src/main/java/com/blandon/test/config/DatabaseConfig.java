package com.blandon.test.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DatabaseConfig {

	   @Bean(name = "dbOracle")
	   @ConfigurationProperties(prefix = "spring.dboracle")
	   @Validated
	   @Primary
	   public DataSource createOracleDataSource() {
		   DataSource ds = new ComboPooledDataSource();
		   return ds;
	   }
	   
	   @Bean(name = "jdbcOracle")
	   @Autowired
	   public JdbcTemplate createOracleJdbcTemplate_(@Qualifier("dbOracle") DataSource oracleDS) {
	      return new JdbcTemplate(oracleDS);
	   }
	   
	   
	   @Bean(name = "dbMaria")
	   @ConfigurationProperties(prefix = "spring.dbmaria")
	   public DataSource createMariaDataSource() {
		   DataSource ds = new ComboPooledDataSource();
		   return ds;
	   }
	   
	   @Bean(name = "jdbcMaria")
	   @Autowired
	   public JdbcTemplate createMariaJdbcTemplate_(@Qualifier("dbMaria") DataSource mariaDS) {
	      return new JdbcTemplate(mariaDS);
	   }
}
