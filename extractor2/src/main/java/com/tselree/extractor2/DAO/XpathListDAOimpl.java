package com.tselree.extractor2.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.tselree.extractor2.model.XpathList;

public class XpathListDAOimpl implements XpathListDAO{
	JdbcTemplate jdbcTemplate;
	
	public XpathListDAOimpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<XpathList> xpathList(String xgroup) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM XPATH_LIST WHERE XGROUP = '"+xgroup+"'";
		
		List<XpathList> listXpath = jdbcTemplate.query(sql, new RowMapper<XpathList>() {

			@Override
			public XpathList mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				XpathList xpath = new XpathList();				
				xpath.setPath(rs.getString("PATH"));
				xpath.setXcolumn(rs.getString("XCOLUMN"));
				xpath.setParent_1(rs.getString("PARENT_1"));
				xpath.setParent_2(rs.getString("PARENT_2"));
				xpath.setMultiplevalue(rs.getBoolean("MULTIPLEVALUE"));
				return xpath;
			}
		});
		return listXpath;
	}

}
