package com.ecommerce.grutils.database

import groovy.sql.Sql;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic Class for creating and closing a datasource
 * 
 * Open the datasource with open, and close() when finished
 * @author daniel
 *
 */
class DatabaseConfig {
	
	private DataSource dataSource
	
	/**
	 * Opens the DataSource
	 * @param username
	 * @param password
	 * @param connectionURI
	 */
	DataSource openDataSource(String username, String password, String connectionURI, String driverClass){
	    BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driverClass);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setUrl(connectionURI);
		dataSource = ds
		getLogger().info "DataSource created"
		return dataSource
	}
	
	/**
	 * Get the dataSource
	 * @return
	 */
	DataSource getDataSource(){
		if(!dataSource){
			getLogger().error "DataSource has not yet been set"
		}
		return dataSource
	}
	
	Sql getSql(){
		def sql = new Sql(getDataSource())
	}
	
	private Logger logger
	
	private Logger getLogger(){
		if(!logger){
			logger = LoggerFactory.getLogger("com.ecommerce.grutils.db.DatabaseConfig")
		}
		return logger
	}
	
	/**
	 * Close the dataSource
	 */
	void close(){
		dataSource.close()
	}
}
