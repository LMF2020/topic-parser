package com.topic.parserAdapter.model;

import java.security.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_topic")
public class Topic {
	@Id
	private int id;
	
	@Column("catalog")
	private char catalog;//题目类型
	
	@Column("content")
	private String content;//题目内容
	
	@Column("answer")
	private String answer; //题目正确答案
	
	@Column("score")
	private float score;//题目得分
	
	@Column("img_url")
	private String imgUrl;//题目包含的图片，多张用逗号隔开
	
	@Column("user_id")
	private int userId;//上传者id号
	
	@Column("hours")
	private int hours;//课时
	
	@Column("class")
	private int className;//年级
	
	@Column("create_time")
	private Timestamp createTime;//创建时间
	
	@Column("course")
	private Integer course;//科目
}
