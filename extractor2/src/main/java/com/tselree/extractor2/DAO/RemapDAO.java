package com.tselree.extractor2.DAO;

public interface RemapDAO {
	public void remapGUID(String table, String column, int key, String delimiter);
}
