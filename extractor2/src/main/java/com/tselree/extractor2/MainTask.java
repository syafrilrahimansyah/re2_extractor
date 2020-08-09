package com.tselree.extractor2;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tselree.extractor2.DAO.EntityDAO;
import com.tselree.extractor2.DAO.OmniformDAO;
import com.tselree.extractor2.DAO.XpathGroupDAO;
import com.tselree.extractor2.DAO.XpathListDAO;
import com.tselree.extractor2.model.PayloadInfo;
import com.tselree.extractor2.model.XpathGroup;
import com.tselree.extractor2.model.XpathList;

@Component
public class MainTask {
	@Autowired
	private OmniformDAO omniformDAO;
	@Autowired
	private XpathGroupDAO xpathGroupDAO;
	@Autowired
	private XpathListDAO xpathListDAO;
	@Autowired
	private EntityDAO entityDAO;
	
	private static final Logger log = LoggerFactory.getLogger(MainTask.class);
	@Scheduled(fixedRate = 5000)
	public void task() throws Exception{
		Integer id = omniformDAO.getNew();
		if(id != null) {
			log.info("New Omniform Found");
			log.info("Fetching Omniform Information");
			String payload_path = omniformDAO.getPath(id);
			PayloadInfo payloadInfo = new XpathCompiler().payloadInfo(payload_path);
			List<XpathGroup> listXpathGroup = xpathGroupDAO.listGroup(payloadInfo.getType());
			log.info("Processing Entity | Type: "+payloadInfo.getType()+" | ID: "+payloadInfo.getId());
			for(XpathGroup xpathGroup : listXpathGroup) {
				log.info("Preparing Extraction | Table: "+ xpathGroup.getEntity_table());
				List<XpathList> listXpathList = xpathListDAO.xpathList(xpathGroup.getXgroup());
				Integer loop_1 = new XpathCompiler().loop_1(payload_path, xpathGroup.getXparent_1());
				for(int x = 1;x<=loop_1;x++) {
					if(!xpathGroup.getXparent_2().equals("")) {
						String key_id = new XpathCompiler().key_id(payload_path, xpathGroup.getKey_xparent(), x, xpathGroup.getKey_xchild());
						entityDAO.delete("`"+xpathGroup.getEntity_table()+"`", xpathGroup.getKey_column(), key_id);
						Integer loop_2 = new XpathCompiler().loop_2(payload_path, xpathGroup.getXparent_1(), x, xpathGroup.getXparent_2());
						for(int y = 1;y<=loop_2;y++) {
							log.info("Extracting: "+xpathGroup.getEntity_table()+" | Index: "+x+" - "+y);
							List<String> listColumn = new ArrayList<>();
							List<String> listValue = new XpathCompiler().extc_value(payload_path, listXpathList, x, y);
							for(XpathList xpathList : listXpathList) {
								listColumn.add(xpathList.getXcolumn());
							}
							String column = String.join(",", listColumn);
							String value = String.join(",", listValue);
							entityDAO.insert("`"+xpathGroup.getEntity_table()+"`", column, value);
						}
					}else {
						log.info("Extracting: "+xpathGroup.getEntity_table()+" | Index: "+x);
						String key_id = new XpathCompiler().key_id(payload_path, xpathGroup.getKey_xparent(), x, xpathGroup.getKey_xchild());
						entityDAO.delete("`"+xpathGroup.getEntity_table()+"`", xpathGroup.getKey_column(), key_id);
						List<String> listColumn = new ArrayList<>();
						List<String> listValue = new XpathCompiler().extc_value(payload_path, listXpathList, x, 1);
						for(XpathList xpathList : listXpathList) {
							listColumn.add(xpathList.getXcolumn());
						}
						String column = String.join(",", listColumn);
						String value = String.join(",", listValue);
						entityDAO.insert("`"+xpathGroup.getEntity_table()+"`", column, value);
					}
				}
			}
			omniformDAO.updateStage(id);
		}else {
			log.info("Searching Omniform");
		}
	}
}
