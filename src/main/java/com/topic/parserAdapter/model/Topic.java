package com.topic.parserAdapter.model;

import java.io.Serializable;
import java.util.Date;

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
	@Comment("主键编号")
	private Integer id;

	@Column("low_num")
	@Comment("小题编号")
	private String lowNum;

	@Column("catalog")
	@ColDefine(type = ColType.VARCHAR, width = 3)
	@Comment("题型(枚举型):1--填空题、2--选择题、3--判断题、4--改错题、5--选词组词题、6--选此组句题、7--作文题、8--临摹题、9--临帖题、10--闪现默写题、11--听写题、12--词语接龙、13--成语接龙、14--解答题")
	private String catalog;

	@Column("title")
	@Comment("题目标题")
	@ColDefine(type = ColType.TEXT)
	private String title;
	
	@Column("content")
	@Comment("题目内容")
	@ColDefine(type = ColType.TEXT)
	private String content;

	@Column("answer")
	@ColDefine(type = ColType.TEXT)
	@Comment("题目答案")
	private String answer;

	@Column("score")
	@Comment("题目分值")
	private String score;

	@Column("fullscore")
	@Comment("题目总分")
	private String fullscore;

	@Column("img_url")
	@ColDefine(type = ColType.TEXT)
	@Comment("图片存放路径，多个用逗号分隔")
	private String imgUrl;

	@Column("user_id")
	@Comment("用户流水号")
	private String userId;

	@Column("school")
	@Comment("学校")
	private String school;
	
	@Column("class")
	@Comment("年级")
	private String className;

	@Column("subject")
	@ColDefine(type = ColType.VARCHAR, width = 3)
	@Comment("科目(数字型)：1--语文、2--数学、3--英语")
	private String subject;

	@Column("hours")
	@Comment("课时")
	private String hours;
	
	@Column("create_time")
	@Comment("上传时间")
	private Date createTime;

	@Column("doc_id")
	@Comment("关联文档Id")
	private Long docId;

	private String catalogName;// 题型名称
	private String subjectName;// 科目名称
	private String createTimeStr;//创建时间

	public Topic() {}

	public Topic(String catalog, String content, String answer, String score,
			String lowNum, String fullscore, String imgUrl) {
		this.catalog = catalog;
		this.content = content;
		this.answer = answer;
		this.score = score;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getFullscore() {
		return fullscore;
	}

	public void setFullscore(String fullscore) {
		this.fullscore = fullscore;
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
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

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("==================小题========================\n")
				.append(" 题号:").append(lowNum).append("\n 题型:").append(catalog)
				.append("\n 内容:").append(content).append("\n 答案:")
				.append(answer).append("\n 分数:").append(score).append(", 图片:")
				.append(imgUrl).append("\n");
		return builder.toString();
	}

}
