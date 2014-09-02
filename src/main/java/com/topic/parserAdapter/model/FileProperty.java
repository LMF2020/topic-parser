package com.topic.parserAdapter.model;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("document")
public class FileProperty implements Serializable {

	private static final long serialVersionUID = -3657087473988616304L;

	@Id
	@Comment("主键")
	private Long docId;

	@Column("user_id")
	@Comment("用户流水号")
	private String uuid;
	
	@Column("class")
	@Comment("年级")
	private String className;
	
	@Column("hours")
	@Comment("课时")
	private String hours;
	
	@Column("subject")
	@Comment("科目")
	private String subject;
	
	@Column("create_time")
	@Comment("创建时间")
	private Date createTime;
	
	@Column("file_name")
	@Comment("文件名称")
	private String fileName;

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
