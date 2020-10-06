package com.tselree.extractor2.DAO;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class OmniformDAOimpl implements OmniformDAO{
	JdbcTemplate jdbcTemplate;
	
	
	public OmniformDAOimpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Integer getNew() {
		try {
			String sql = "SELECT MIN(ID) FROM extc_omniform WHERE STAGE = 'col'";
			return jdbcTemplate.queryForObject(sql, Integer.class);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public String getPath(Integer id) {
		try {
			String sql = "SELECT PATH FROM extc_omniform WHERE ID = '"+id+"'";
			return jdbcTemplate.queryForObject(sql, String.class);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void updateStage(Integer id) {
		String sql = "UPDATE extc_omniform SET STAGE = 'ext' WHERE ID = '"+id+"'";
		jdbcTemplate.update(sql);		
	}

	

}
