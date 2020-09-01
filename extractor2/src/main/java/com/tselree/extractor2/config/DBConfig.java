package com.tselree.extractor2.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@ComponentScan(basePackages="com.tselree.extractor2")
public class DBConfig {
	@Bean()
    public DataSource getDataSource2() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/extractor2?serverTimezone=UTC&useLegacyDatetimeCode=false");
        dataSource.setUsername("pmauser");
        dataSource.setPassword("alvin147");
         
        return dataSource;
    }
	
	/*
	 * @Bean() public DataSource getDataSource3() { DriverManagerDataSource
	 * dataSource = new DriverManagerDataSource();
	 * dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); dataSource.setUrl(
	 * "jdbc:mysql://localhost:3306/entity?serverTimezone=UTC&useLegacyDatetimeCode=false"
	 * ); dataSource.setUsername("pmauser"); dataSource.setPassword("alvin147");
	 * 
	 * return dataSource; }
	 */
	
	@Bean()
    public DataSource getDataSource3() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://parakoder.com:3306/parakode_re2_entity?serverTimezone=UTC&useLegacyDatetimeCode=false");
        dataSource.setUsername("parakode_re2_entity");
        dataSource.setPassword("re2Ent1ty");
         
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
