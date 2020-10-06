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
		String sql = "INSERT INTO extc_map_list(\"table\", \"column\", \"key\", \"delimiter\", stage) VALUES('"+table+"', '"+column+"', "+key+", '"+delimiter+"', 'ext')";
		jdbcTemplate.update(sql);
	}

}
