package com.tselree.extractor2.DAO;

public interface EntityDAO {
	public void insert(String table, String column, String value);
	public void delete(String table, String column, String value);
}
