package com.topic.parserAdapter.adapter.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;

import com.topic.parserAdapter.core.util.IPUtil;
import com.topic.parserAdapter.dao.TopicTypeDao;
import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.Topic;
import com.topic.parserAdapter.model.TopicType;
import com.topic.parserAdapter.utils.MyServletContext;

@At("/service")
@InjectName
@IocBean
public class TopicAdapter {
	@Inject
	private TopicTypeDao topicTypeDao;

	/**
	 * 根据用户id查询文档信息
	 * 
	 * @param doc
	 * @param sc
	 * @param errCtx
	 * @return
	 */
	@At("/topic/getDocList")
	@Ok("json:{quoteName:true, ignoreNull:true}")
	@Fail("http:500")
	public Map<String, Object> getDocList(@Param("..") Document doc,
			AdaptorErrorContext errCtx) {
		if (errCtx != null) {
			System.out.println("查询文档信息出错：" + errCtx.getErrors()[0]);
		}
		int code = 1; // 状态码：1失败、0成功
		String msg = "获取文档信息失败";
		Map<String, Object> mm = new HashMap<String, Object>();
		List<Document> docList = null;// 文档列表
		if (doc != null && doc.getUserId() != null) {
			System.out.println("查询用户【" + doc.getUserId() + "】所拥有的文档");
			Criteria cri = Cnd.cri();// 复杂组合查询
			cri.where().andEquals("user_id", doc.getUserId());
			if (doc.getHours() != null) {
				cri.where().andEquals("hours", doc.getHours());
			}
			if (doc.getSubject() != null) {
				cri.where().andEquals("subject", doc.getSubject());
			}
			if (doc.getClassName() != null) {
				cri.where().andEquals("className", doc.getClassName());
			}
			docList = topicTypeDao.search(Document.class, cri);
		}
		if (docList != null && docList.size() > 0) {
			code = 0;
			msg = "获取文档信息成功";
			List<Map<String, Object>> ml = new ArrayList<Map<String, Object>>();
			for (Document d : docList) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("docId", d.getDocId());
				m.put("fileName", d.getFileName());
				m.put("userId", d.getUserId());
				m.put("school", d.getSchool());
				m.put("className", d.getClassName());
				ml.add(m);// 添加list对象
			}
			mm.put("list", ml);
		}
		mm.put("code", code);
		mm.put("msg", msg);
		String ret = Json.toJson(mm);// 转换成json
		System.out.println("返回报文-->\n" + ret);
		return mm;
	}

	
	/**
	 * 获取文档内容的查询接口
	 * 
	 * @param topic
	 * @param errCtx
	 * @return
	 */
	@At("/topic/getTopicList")
	@Ok("json:{quoteName:true, ignoreNull:true}")
	@Fail("http:500")
	public Map<String, Object> getTopicList(@Param("..") Topic topic,
			AdaptorErrorContext errCtx, HttpServletRequest req) {

		if (errCtx != null) {
			System.out.println("查询文档内容出错：" + errCtx.getErrors()[0]);
		}
		int code = 1; // 状态码：1失败、0成功
		String msg = "获取文档内容失败";
		List<TopicType> ttList = null;
		Map<String, Object> m = new HashMap<String, Object>();
		List<Topic> tList = null;
		if (topic != null && topic.getDocId() != null) {
			m.put("docId", topic.getDocId());
			System.out.println("用户查询ID=【" + topic.getDocId() + "】的文档内容信息");
			ttList = topicTypeDao.getTopicTypeList(topic.getDocId(),
					topic.getCatalog());
			System.out.println("查询题型结束，共计【" + ttList.size() + "】种题型");
			System.out.println("开始查询具体的题目列表--->");
			Criteria cri = Cnd.cri();// 复杂组合查询
			cri.where().andEquals("doc_id", topic.getDocId());
			if (topic.getCatalog() != null) {
				cri.where().andEquals("catalog", topic.getCatalog());
			}
			tList = topicTypeDao.search(Topic.class, cri);
			System.out.println("查询具体题目结束，共计【" + tList.size() + "】道题");
		}
		if (tList != null & tList.size() > 0) {
			code = 0;
			msg = "获取文档内容成功";
			List<Map<String, Object>> mml = new ArrayList<Map<String, Object>>();
			for (TopicType tt : ttList) {
				Map<String, Object> mmt = new HashMap<String, Object>();
				List<Map<String, Object>> ml = new ArrayList<Map<String, Object>>();
				for (Topic t : tList) {
					if (tt.getTopicTypeNum() == Integer
							.parseInt(t.getCatalog())) {
						Map<String, Object> mt = new HashMap<String, Object>();
						mt.put("topicId", t.getId());
						mt.put("lowNum", t.getLowNum());
						mt.put("content", t.getContent()  //回显替换IP地址
								.replace("${server}", IPUtil.getServerAddr(req)));
						mt.put("answer", t.getAnswer());
						mt.put("score", t.getScore());
						mt.put("imgUrl", t.getImgUrl());
						ml.add(mt);
					}
				}
				mmt.put("topics", ml);
				mmt.put("catalog", tt.getTopicTypeNum());
				mmt.put("cataName", tt.getTopicType());
				mmt.put("title", tt.getTitle());
				mmt.put("topicsCount", tt.getTypeCount());
				mmt.put("fullScore", tt.getFullScore());
				mml.add(mmt);
			}
			m.put("list", mml);
		}
		m.put("code", code);
		m.put("msg", msg);
		String ret = Json.toJson(m);
		System.out.println("返回报文-->\n" + ret);
		return m;
	}
}
