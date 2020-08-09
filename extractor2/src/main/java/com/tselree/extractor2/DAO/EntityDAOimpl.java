package com.tselree.extractor2.DAO;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tselree.extractor2.MainTask;

public class EntityDAOimpl implements EntityDAO{
	JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(MainTask.class);
	
	public EntityDAOimpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	public void insert(String table, String column, String value) {
		try {
			String sql = "INSERT INTO "+table+"("+column+") VALUES("+value+")";
			jdbcTemplate.update(sql);
		}catch(DuplicateKeyException e) {
			log.info(e.getMessage());
		}
	}


	@Override
	public void delete(String table, String column, String value) {
		String sql = "DELETE FROM "+table+" WHERE "+column+" = '"+value+"'";
		jdbcTemplate.update(sql);
	}
}
