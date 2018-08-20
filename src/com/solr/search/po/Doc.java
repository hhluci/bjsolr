package com.solr.search.po;

public class Doc {
	String file;
	long size;
	String content;
	String fileName;
	String hlName;
	public Doc() {
		
	}
	public Doc(String file, long size, String content,String fileName,String hlName) {
		this.file = file;
		this.size = size;
		this.content = content;
		this.fileName = fileName;
		this.hlName = hlName;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getHlName() {
		return hlName;
	}
	public void setHlName(String hlName) {
		this.hlName = hlName;
	}
	
}
