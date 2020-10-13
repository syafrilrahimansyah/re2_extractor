package com.tselree.extractor2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.tselree.extractor2.DAO.XpathListDAO;
import com.tselree.extractor2.DAO.XpathListDAOimpl;
import com.tselree.extractor2.DAO.EntityDAO;
import com.tselree.extractor2.DAO.EntityDAOimpl;
import com.tselree.extractor2.DAO.OmniformDAO;
import com.tselree.extractor2.DAO.OmniformDAOimpl;
import com.tselree.extractor2.DAO.RemapDAO;
import com.tselree.extractor2.DAO.RemapDAOimpl;
import com.tselree.extractor2.DAO.XpathGroupDAO;
import com.tselree.extractor2.DAO.XpathGroupDAOimpl;

@Configuration
@PropertySource({
    "file:src/main/resources/extractor_application.properties" 
})

public class DBConfig {
	@Autowired
    Environment env;
	
	@Bean()
    public DataSource getDataSource2() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("DB_DRIVER"));
        dataSource.setUrl(env.getProperty("DB_URL"));
        dataSource.setUsername(env.getProperty("DB_USERNAME"));
        dataSource.setPassword(env.getProperty("DB_PASSWORD"));
         
        return dataSource;
    }
	
	@Bean()
    public DataSource getDataSource3() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("DB_DRIVER"));
        dataSource.setUrl(env.getProperty("DB_URL"));
        dataSource.setUsername(env.getProperty("DB_USERNAME"));
        dataSource.setPassword(env.getProperty("DB_PASSWORD"));
         
        return dataSource;
    }
	@Bean()
    public OmniformDAO getOmniformDAO() {
    	return new OmniformDAOimpl(getDataSource2());
    }
	@Bean()
	public XpathGroupDAO getXpathGroupDAO() {
		return new XpathGroupDAOimpl(getDataSource2());
	}
	@Bean()
	public XpathListDAO getXpathListDAO() {
		return new XpathListDAOimpl(getDataSource2());
	}
	@Bean()
	public EntityDAO getEntityDAO() {
		return new EntityDAOimpl(getDataSource3());
	}
	@Bean
	public RemapDAO getRemapDAO() {
		return new RemapDAOimpl(getDataSource2());
	}
}
