package com.topic.parserAdapter.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.IocBean;

import com.topic.parserAdapter.model.TopicType;

@IocBean
public class TopicTypeDao extends BasicDao {
	
	/**
	 * 复杂查询，model无表对应
	 * c 对应model
	 * s 对应sql，where前面的部分
	 * c 对应where后面的条件:condition = "where id=1"...
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TopicType> queryByNativeSql(@SuppressWarnings("rawtypes") Class c, String s, String condition) {
		Sql sql = Sqls.create(s);
		sql.setCondition(Cnd.wrap(condition)).setCallback(new SqlCallback() {
			List<TopicType> ttl = new ArrayList<TopicType>();
			@Override
			public List<TopicType> invoke(Connection conn, ResultSet rs, Sql sql)
					throws SQLException {
				int id = 1;
				while(rs.next()){
					TopicType tt = new TopicType();
					tt.setTypeId(id++);
					tt.setTopicType(rs.getInt("topicType"));
					tt.setTopicTypeNum(rs.getInt("topicType"));
					tt.setTypeCount(rs.getInt("typeCount"));
					tt.setFullScore(rs.getFloat("fullScore"));
					tt.setDocId(rs.getLong("docId"));
					tt.setTitle(rs.getString("title"));
					ttl.add(tt);
				}
				System.out.println("查询题型成功，共查询【" + ttl.size() + "】道题目");
				return ttl;
			}
		});
		dao.execute(sql);//执行sql
		List<TopicType> list = sql.getList(TopicType.class);
		return list;
	}
	
	
	/**
	 * 获取题型list，根据文档id和题型id
	 * @param docId
	 * @param catalog
	 * @return
	 */
	public List<TopicType> getTopicTypeList(Long docId, String catalog){
		String sql = "SELECT catalog as topicType,title,COUNT(catalog) as typeCount, fullScore,doc_id as docId from t_topic $condition GROUP BY catalog ORDER BY id ASC";
		String condition = "where doc_id=" + docId;
		if(catalog != null && catalog != "") condition += " and catalog='" + catalog + "'";
		System.out.println("sql-->"+sql+"\n condition-->"+condition);
		List<TopicType> tl = queryByNativeSql(TopicType.class, sql, condition);
		return tl;
	}
	
}
