package com.topic.parserAdapter.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_topic")
public class Topic implements Serializable {
	// 生产随机序列号
	private static final long serialVersionUID = 4364265353467878632L;

	@Id
	@Comment("主键id编号")
	private Integer id;

	@Column("low_num")
	@Comment("小题编号")
	private String lowNum; // 小题编号
	
	@Column("catalog")
	@ColDefine(type=ColType.VARCHAR, width=3)
	@Comment("题型(枚举型):1--填空题、2--选择题、3--判断题、4--改错题、5--选词组词题、6--选此组句题、7--作文题、8--临摹题、9--临帖题、10--闪现默写题、11--听写题、12--词语接龙、13--成语接龙、14--解答题")
	private String catalog;// 题目类型

	@Column("content")
	@ColDefine(type=ColType.TEXT)
	@Comment("题目内容")
	private String content;// 题目内容

	@Column("answer")
	@ColDefine(type=ColType.TEXT)
	@Comment("题目正确答案")
	private String answer; // 题目正确答案

	@Column("score")
	@Comment("分值")
	private String score;// 题目得分

	@Column("img_url")
	@ColDefine(type=ColType.TEXT)
	@Comment("图片存放路径，多个用逗号分隔")
	private String imgUrl;// 题目包含的图片，多张用逗号隔开

	@Column("user_id")
	@Comment("上传者id")
	private String userId;// 上传者id号

	@Column("hours")
	@Comment("课时")
	private String hours;// 课时

	@Column("class")
	@Comment("年级")
	private String className;// 年级

	@Column("create_time")
	@Comment("提交时间")
	private Timestamp createTime;// 创建时间

	@Column("course")
	@ColDefine(type=ColType.VARCHAR, width=3)
	@Comment("科目(数字型)：1--语文、2--数学、3--英语")
	private String subject;// 科目

	public Topic() {
	}

	public Topic(String catalog, String content, String answer, String score,
			String subject) {
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
