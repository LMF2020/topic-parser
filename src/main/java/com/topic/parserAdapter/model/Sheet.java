package com.topic.parserAdapter.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.Table;

@Table("t_sheet")
public class Sheet {
	
	@Id
	@Comment("作业编号")
	private Integer id;
	
	@Column("content")
	@Comment("作业内容")
	@ColDefine(type = ColType.TEXT)
	private String content;
	
	@Column("doc_id")
	@Comment("关联文档编号")
	@ColDefine(notNull = true)
	private Integer docId;
	
	@Column("stu_id")
	@Comment("关联学生编号")
	@ColDefine(notNull = true)
	private Integer stuId;
	
	@Column("commit_time")
	@Comment("提交时间")
	@ColDefine(type = ColType.DATETIME)
	private Date commitTime;
	
	@Column("state")
	@Comment("试卷状态")
	@ColDefine(type = ColType.VARCHAR, width = 1)
	private String state;
	
	@Readonly  //查询开始日期
	private String startDate;
	
	@Readonly  //查询结束日期
	private String endDate;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public Integer getStuId() {
		return stuId;
	}

	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
