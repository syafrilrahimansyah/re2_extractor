package com.tselree.extractor2.DAO;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class RemapDAOimpl implements RemapDAO{
	JdbcTemplate jdbcTemplate;
	
	public RemapDAOimpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void remapGUID(String table, String column, int key, String delimiter) {
		String sql = "INSERT INTO `MAP_LIST`(`ID`, `TABLE`, `COLUMN`, `KEY`, `DELIMITER`, `STAGE`) VALUES(NULL, '"+table+"', '"+column+"', "+key+", '"+delimiter+"', 'ext')";
		jdbcTemplate.update(sql);
	}

}
