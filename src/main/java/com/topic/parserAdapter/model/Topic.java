package com.topic.parserAdapter.model;

import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_topic")
public class Topic {
	@Id
	private Integer id;

	@Column("catalog")
	private Integer catalog;//题目类型
	
	@Column("content")
	private String content;//题目内容
	
	@Column("answer")
	private String answer; //题目正确答案
	
	@Column("score")
	private Float score;//题目得分
	
	@Column("img_url")
	private String imgUrl;//题目包含的图片，多张用逗号隔开
	
	@Column("user_id")
	private Integer userId;//上传者id号
	
	@Column("hours")
	private Integer hours;//课时
	
	@Column("class")
	private Integer className;//年级
	
	@Column("create_time")
	private Timestamp createTime;//创建时间
	
	@Column("course")
	private Integer course;//科目
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCatalog() {
		return catalog;
	}

	public void setCatalog(Integer catalog) {
		this.catalog = catalog;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getClassName() {
		return className;
	}

	public void setClassName(Integer className) {
		this.className = className;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCourse() {
		return course;
	}

	public void setCourse(Integer course) {
		this.course = course;
	}

}
