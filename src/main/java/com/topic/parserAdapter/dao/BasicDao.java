package com.topic.parserAdapter.dao;

import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.topic.parserAdapter.model.Topic;

public interface BaseDao {
	//查询所有的Topic题目
	public abstract List<Topic> findTopicList(Connection conn) throws SQLException;
	
	//根据id来查询具体的题目
	public abstract Topic findTopicById(Connection conn, int id) throws SQLException;
	
	//插入题目
	public abstract boolean insertTopic(Connection conn, Topic topic) throws SQLException;
	
	//批量插入
	public abstract boolean insertTopicBat(Connection conn, List<Topic> topicList) throws SQLException;
}
