package com.topic.parserAdapter.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_topic")
public class Topic implements Serializable{
	//生产随机序列号
	private static final long serialVersionUID = 4364265353467878632L;

	@Id
	private Integer id;

	@Column("high_num")
	private String highNum; //大题编号
	
	@Column("low_num")
	private String lowNum;  //小题编号
	
	@Column("catalog")
	private String catalog;//题目类型
	
	@Column("content")
	private String content;//题目内容
	
	@Column("answer")
	private String answer; //题目正确答案
	
	@Column("score")
	private String score;//题目得分
	
	@Column("img_url")
	private String imgUrl;//题目包含的图片，多张用逗号隔开
	
	@Column("user_id")
	private String userId;//上传者id号
	
	@Column("hours")
	private String hours;//课时
	
	@Column("class")
	private String className;//年级
	
	@Column("create_time")
	private Timestamp createTime;//创建时间
	
	@Column("course")
	private String subject;//科目
	
	public Topic(){}
	public Topic(String catalog, String content,String answer,String score,String subject){
		this.catalog = catalog;
		this.content = content;
		this.answer = answer;
		this.score = score;
		this.subject = subject;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHighNum() {
		return highNum;
	}
	
	public void setHighNum(String highNum) {
		this.highNum = highNum;
	}
	
	public String getLowNum() {
		return lowNum;
	}
	
	public void setLowNum(String lowNum) {
		this.lowNum = lowNum;
	}
	
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
