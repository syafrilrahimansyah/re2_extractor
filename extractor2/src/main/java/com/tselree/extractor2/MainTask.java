package com.tselree.extractor2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tselree.extractor2.DAO.EntityDAO;
import com.tselree.extractor2.DAO.OmniformDAO;
import com.tselree.extractor2.DAO.RemapDAO;
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
	@Autowired
	private RemapDAO remapDAO;
	
	private static final Logger log = LoggerFactory.getLogger(MainTask.class);
	@Scheduled(fixedRate = 5000)
	public void task() throws Exception{
		Integer id = omniformDAO.getNew();
		if(id != null) {
			log.info("New Omniform Found");
			log.info("Fetching Omniform Information");
			//INITIATION
			String payload_path = omniformDAO.getPath(id);
			PayloadInfo payloadInfo = new XpathCompiler().payloadInfo(payload_path);
			List<XpathGroup> listXpathGroup = xpathGroupDAO.listGroup(payloadInfo.getType());
			log.info("Processing Entity | Type: "+payloadInfo.getType()+" | ID: "+payloadInfo.getId());
			for(XpathGroup xpathGroup : listXpathGroup) {
				log.info("Preparing Extraction | Table: "+ xpathGroup.getEntity_table());
				List<XpathList> listXpathList = xpathListDAO.xpathList(xpathGroup.getXgroup());
				//XPATH NODE CALCULATION
				Integer loop_1 = new XpathCompiler().loop_1(payload_path, xpathGroup.getXparent_1());
				for(int x = 1;x<=loop_1;x++) {
					//LOOP NODE
					if(!xpathGroup.getXparent_2().equals("")) {
						//LOOP NODE'
						String key_id = new XpathCompiler().key_id(payload_path, xpathGroup.getKey_xparent(), x, xpathGroup.getKey_xchild());
						entityDAO.delete(xpathGroup.getEntity_table(), xpathGroup.getKey_column(), key_id);
						Integer loop_2 = new XpathCompiler().loop_2(payload_path, xpathGroup.getXparent_1(), x, xpathGroup.getXparent_2());
						for(int y = 1;y<=loop_2;y++) {
							log.info("Extracting: "+xpathGroup.getEntity_table()+" | Index: "+x+" - "+y);
							List<String> listColumn = new ArrayList<>();
							//EXTRACTION
							List<String> listValue = new XpathCompiler().extc_value(payload_path, listXpathList, x, y);
							for(XpathList xpathList : listXpathList) {
								listColumn.add(xpathList.getXcolumn());
							}
							//DATE FORMAT
							DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
							Date dateobj = new Date();
							//MODIFIED
							listColumn.add("MODIFIED");
							listValue.add("'"+df.format(dateobj)+"'");
							//JOIN DATA
							String column = String.join(",", listColumn);
							String value = String.join(",", listValue);
							//ENTITY INSERTION
							try {
								int key_map = entityDAO.insert(xpathGroup.getEntity_table(), column, value);
								//GUID REMAP LIST
								for(XpathList xpathList : listXpathList) {
									if(xpathList.getRemap()) {
										remapDAO.remapGUID(xpathGroup.getEntity_table(), xpathList.getXcolumn(), key_map, xpathList.getRemap_delimiter());
									}
								}
							}catch(NullPointerException e) {
								System.out.println(e);
							}
						}
					}else {
						log.info("Extracting: "+xpathGroup.getEntity_table()+" | Index: "+x);
						String key_id = new XpathCompiler().key_id(payload_path, xpathGroup.getKey_xparent(), x, xpathGroup.getKey_xchild());
						entityDAO.delete(xpathGroup.getEntity_table(), xpathGroup.getKey_column(), key_id);
						List<String> listColumn = new ArrayList<>();
						//EXTRACTION
						List<String> listValue = new XpathCompiler().extc_value(payload_path, listXpathList, x, 1);
						for(XpathList xpathList : listXpathList) {
							listColumn.add(xpathList.getXcolumn());
						}
						//DATE FORMAT
						DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
						Date dateobj = new Date();
						//MODIFIED
						listColumn.add("MODIFIED");
						listValue.add("'"+df.format(dateobj)+"'");
						//JOIN DATA
						String column = String.join(",", listColumn);
						String value = String.join(",", listValue);
						
						//ENTITY INSERTION
						try {
							int key_map = entityDAO.insert(xpathGroup.getEntity_table(), column, value);
							//GUID REMAP LIST
							for(XpathList xpathList : listXpathList) {
								if(xpathList.getRemap()) {
									remapDAO.remapGUID(xpathGroup.getEntity_table(), xpathList.getXcolumn(), key_map, xpathList.getRemap_delimiter());
								}
							}
						}catch(NullPointerException e) {
							System.out.println(e);
						}
					}
				}
			}
			omniformDAO.updateStage(id);
		}else {
			log.info("Searching Omniform");
		}
	}
}
