package com.topic.parserAdapter.model;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_user")
public class User implements Serializable {
	// 生产随机序列号
	private static final long serialVersionUID = -2513381853146410926L;

	@Id
	@Comment("主键编号")
	private Integer id;

	@Column("user_id")
	@Comment("用户流水号")
	private String userId;

	@Column("user_pwd")
	@Comment("用户登录密码")
	private String userPwd;

	@Column("school")
	@Comment("学校")
	private String school;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
}
