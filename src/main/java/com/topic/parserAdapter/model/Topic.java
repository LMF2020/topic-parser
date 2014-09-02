package com.topic.parserAdapter.model;

import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_topic")
public class Topic {
	
	@Id
	private Integer id;

	@Column("high_num")
	private String highNum; //大题编号
	
	@Column("low_num")
	private String lowNum;  //小题编号
	
	@Column("catalog")
	private String catalog;//题目类型
	
	@Column("content")
	@ColDefine(type=ColType.TEXT)
	private String content;//题目内容
	
	@Column("answer")
	private String answer; //题目正确答案
	
	@Column("score")
	private String score;//题目得分
	
	@Column("fullscore")
	private String fullscore;//题目总分
	
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
	public Topic(String catalog, String content,String answer,String score,
			String subject,String lowNum,String fullscore,String imgUrl){
		this.catalog = catalog;
		this.content = content;
		this.answer = answer;
		this.score = score;
		this.subject = subject;
		this.lowNum = lowNum;
		this.fullscore = fullscore;
		this.imgUrl = imgUrl;
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
	
	public String getFullscore() {
		return fullscore;
	}
	
	public void setFullscore(String fullscore) {
		this.fullscore = fullscore;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("==================小题========================\n").
		append(" 题号:").append(lowNum).append("\n 题型:")
				.append(catalog).append("\n 内容:").append(content)
				.append("\n 答案:").append(answer).append("\n 分数:")
				.append(score).append(", 图片:").append(imgUrl).append("\n");
		return builder.toString();
	}
	
	
}
