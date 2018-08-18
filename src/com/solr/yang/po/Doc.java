package com.solr.yang.po;

public class Doc {
	String file;
	long size;
	String content;
	String url;
	public Doc() {
		
	}
	public Doc(String file, long size, String content,String url) {
		this.file = file;
		this.size = size;
		this.content = content;
		this.url = url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
