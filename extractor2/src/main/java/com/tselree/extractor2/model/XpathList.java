package com.tselree.extractor2.model;

public class XpathList {
	private String xcolumn;
	private String path;
	private String parent_1;
	private String parent_2;
	private Boolean multiplevalue;
	private Boolean remap;
	private String remap_delimiter;
	
	
	public String getRemap_delimiter() {
		return remap_delimiter;
	}
	public void setRemap_delimiter(String remap_delimiter) {
		this.remap_delimiter = remap_delimiter;
	}
	public Boolean getRemap() {
		return remap;
	}
	public void setRemap(Boolean remap) {
		this.remap = remap;
	}
	public String getXcolumn() {
		return xcolumn;
	}
	public void setXcolumn(String xcolumn) {
		this.xcolumn = xcolumn;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getParent_1() {
		return parent_1;
	}
	public void setParent_1(String parent_1) {
		this.parent_1 = parent_1;
	}
	public String getParent_2() {
		return parent_2;
	}
	public void setParent_2(String parent_2) {
		this.parent_2 = parent_2;
	}
	public Boolean getMultiplevalue() {
		return multiplevalue;
	}
	public void setMultiplevalue(Boolean multiplevalue) {
		this.multiplevalue = multiplevalue;
	}
	
}
