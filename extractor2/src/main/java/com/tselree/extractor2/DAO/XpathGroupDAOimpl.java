package com.tselree.extractor2.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.tselree.extractor2.model.XpathGroup;

public class XpathGroupDAOimpl implements XpathGroupDAO{
	JdbcTemplate jdbcTemplate;
	
	public XpathGroupDAOimpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<XpathGroup> listGroup(String type) {
		try {
			// TODO Auto-generated method stub
			String sql = "SELECT * FROM XPATH_CONF WHERE OMNIFORM_TYPE = '"+type+"'";
			List<XpathGroup> xpathGroup = jdbcTemplate.query(sql, new RowMapper<XpathGroup>() {
				@Override
				public XpathGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
					// TODO Auto-generated method stub
					XpathGroup xpathGroup = new XpathGroup();
					xpathGroup.setXgroup(rs.getString("XGROUP"));
					xpathGroup.setEntity_table(rs.getString("ENTITY_TABLE"));
					xpathGroup.setKey_column(rs.getString("KEY_COLUMN"));
					xpathGroup.setKey_xparent(rs.getString("KEY_XPARENT"));
					xpathGroup.setKey_xchild(rs.getString("KEY_XCHILD"));
					xpathGroup.setDynamic_key(rs.getString("DYNAMIC_KEY"));
					xpathGroup.setXparent_1(rs.getString("XPARENT_1"));
					xpathGroup.setXparent_2(rs.getString("XPARENT_2"));
					return xpathGroup;
				}});
			return xpathGroup;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
