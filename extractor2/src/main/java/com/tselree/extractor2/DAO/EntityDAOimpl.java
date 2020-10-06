package com.tselree.extractor2.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.tselree.extractor2.MainTask;

public class EntityDAOimpl implements EntityDAO{
	JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(MainTask.class);
	
	public EntityDAOimpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	public Integer insert(String table, String column, String value) {
		try {
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			String sql = "INSERT INTO re2ety_"+table+"("+column+") VALUES("+value+")";
			jdbcTemplate.update(new PreparedStatementCreator() {
			    @Override
			    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			        return statement;
			    }
			}, holder);
			Integer id = (Integer) holder.getKeys().get("id");
			return id;
		}catch(Exception e) {
			log.info(e.getMessage());
			return null;
		}
		
	}


	@Override
	public void delete(String table, String column, String value) {
		String sql = "DELETE FROM re2ety_"+table+" WHERE "+column+" = '"+value+"'";
		jdbcTemplate.update(sql);
	}
}
