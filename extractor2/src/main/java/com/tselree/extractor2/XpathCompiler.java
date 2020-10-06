package com.tselree.extractor2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.tselree.extractor2.model.PayloadInfo;
import com.tselree.extractor2.model.XpathList;

public class XpathCompiler {
	public PayloadInfo payloadInfo(String xml) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xml);

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		PayloadInfo check = new PayloadInfo();
		check.setId(xpath.evaluate("/Root/Package/@BusinessID", document));
		
		if(!xpath.evaluate("/Root/Package/@type", document).equals("")) {
			check.setType(xpath.evaluate("/Root/Package/@type", document));
		}
		else if(!xpath.evaluate("/Root/Product_Zones/@type", document).equals("")) {
			check.setType(xpath.evaluate("/Root/Product_Zones/@type", document));
		}
		else if(!xpath.evaluate("/Root/Price_Zones/@type", document).equals("")) {
			check.setType(xpath.evaluate("/Root/Price_Zones/@type", document));
		}
		else {
			check.setType(null);
		}
		return check;
	}
	public String key_id(String xml, String key_parent, int x, String key_child) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xml);

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		return xpath.evaluate(key_parent+"["+x+"]"+key_child, document);
	}
	public Integer loop_1(String xml, String parent_1) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xml);
        
        XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		XPathExpression expr = xpath.compile(parent_1);
		Object result = expr.evaluate(document, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		return nodes.getLength();
	}
	public Integer loop_2(String xml, String parent_1, int x, String parent_2) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xml);
        
        XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		XPathExpression expr = xpath.compile(parent_1+"["+x+"]"+parent_2);
		Object result = expr.evaluate(document, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		return nodes.getLength();
	}
	public List<String> extc_value(String xml, List<XpathList> listXpathList, int x, int y) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xml);

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		List<String> valueList = new ArrayList<>();
		
		for(XpathList xpathList : listXpathList) {
			if(xpathList.getMultiplevalue()) {
				if(xpathList.getParent_1().equals("") && xpathList.getParent_2().equals("")) {
					XPathExpression expr = xpath.compile(xpathList.getPath());
					Object result = expr.evaluate(document, XPathConstants.NODESET);
			        NodeList nodes = (NodeList) result;
			        List<String> resultList = new ArrayList<>();
			        for (int i = 0; i < nodes.getLength(); i++) {
			        	resultList.add(nodes.item(i).getNodeValue());
			        }
					valueList.add("'"+String.join("~", resultList)+"'");
				}
				else if(!xpathList.getParent_1().equals("") && xpathList.getParent_2().equals("")) {
					XPathExpression expr = xpath.compile(xpathList.getParent_1()+"["+x+"]"+xpathList.getPath());
					Object result = expr.evaluate(document, XPathConstants.NODESET);
			        NodeList nodes = (NodeList) result;
			        List<String> resultList = new ArrayList<>();
			        for (int i = 0; i < nodes.getLength(); i++) {
			        	resultList.add(nodes.item(i).getNodeValue());
			        }
					valueList.add("'"+String.join("~", resultList)+"'");
				}else {
					XPathExpression expr = xpath.compile(xpathList.getParent_1()+"["+x+"]"+xpathList.getParent_2()+"["+y+"]"+xpathList.getPath());
					Object result = expr.evaluate(document, XPathConstants.NODESET);
			        NodeList nodes = (NodeList) result;
			        List<String> resultList = new ArrayList<>();
			        for (int i = 0; i < nodes.getLength(); i++) {
			        	resultList.add(nodes.item(i).getNodeValue());
			        }
					valueList.add("'"+String.join("~", resultList)+"'");
				}				
		        
			}else {
				if(xpathList.getParent_1().equals("") && xpathList.getParent_2().equals("")) {
					String result = xpath.evaluate(xpathList.getPath(), document);
					if(result.equals("")) {
						valueList.add(null);
					}else {
						valueList.add("'"+result+"'");
					}					
				}
				else if(!xpathList.getParent_1().equals("") && xpathList.getParent_2().equals("")) {
					String result = xpath.evaluate(xpathList.getParent_1()+"["+x+"]"+xpathList.getPath(), document);
					if(result.equals("")) {
						valueList.add(null);
					}else {
						valueList.add("'"+result+"'");
					}
				}else {
					String result = xpath.evaluate(xpathList.getParent_1()+"["+x+"]"+xpathList.getParent_2()+"["+y+"]"+xpathList.getPath(), document);
					if(result.equals("")) {
						valueList.add(null);
					}else {
						valueList.add("'"+result+"'");
					}
				}
			}
		}
		return valueList;
	}

}
