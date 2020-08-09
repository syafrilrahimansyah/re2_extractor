package com.tselree.extractor2.DAO;
import java.util.List;

import com.tselree.extractor2.model.XpathGroup;
public interface XpathGroupDAO {
	public List<XpathGroup> listGroup(String type);
}
