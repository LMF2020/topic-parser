package com.topic.parserAdapter.model;

import java.io.Serializable;

public class FileProperty implements Serializable {
	// 生产随机序列号
	private static final long serialVersionUID = -3657087473988616304L;

	private long docId;// 文档id
	private String uuid;// 文档上传者id
	private String hours;// 文档所属课时
	private String className;// 文档所属年级
	private String subject;// 科目

	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
