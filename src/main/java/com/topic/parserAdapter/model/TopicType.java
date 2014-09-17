package com.topic.parserAdapter.model;

import java.io.Serializable;

public class TopicType implements Serializable {

	private static final long serialVersionUID = 4346537367959824841L;

	private int typeId;// 题型编号
	private String topicType;// 题型
	private int topicTypeNum;//题型编号
	private int typeCount;// 题目总数
	private float fullScore;// 总分
	private Long docId;// 所属文档ID

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(int type) {
		switch (type) {
		case 1:
			this.topicType = "填空题";
			break;
		case 2:
			this.topicType = "选择题";
			break;
		case 3:
			this.topicType = "判断题";
			break;
		case 4:
			this.topicType = "改错题";
			break;
		case 5:
			this.topicType = "选词组词题";
			break;
		case 6:
			this.topicType = "选此组句题";
			break;
		case 7:
			this.topicType = "作文题";
			break;
		case 8:
			this.topicType = "临摹题";
			break;
		case 9:
			this.topicType = "临帖题";
			break;
		case 10:
			this.topicType = "闪现默写题";
			break;
		case 11:
			this.topicType = "听写题";
			break;
		case 12:
			this.topicType = "词语接龙";
			break;
		case 13:
			this.topicType = "成语接龙";
			break;
		case 14:
			this.topicType = "解答题";
			break;
		default:
			this.topicType = "课文";
			break;
		}
	}

	public int getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(int typeCount) {
		this.typeCount = typeCount;
	}

	public float getFullScore() {
		return fullScore;
	}

	public void setFullScore(float fullScore) {
		this.fullScore = fullScore;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public int getTopicTypeNum() {
		return topicTypeNum;
	}

	public void setTopicTypeNum(int topicTypeNum) {
		this.topicTypeNum = topicTypeNum;
	}
}
