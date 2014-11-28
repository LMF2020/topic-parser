package com.topic.parserAdapter.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;

import com.topic.parserAdapter.core.util.IPUtil;
import com.topic.parserAdapter.dao.TopicTypeDao;
import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.Sheet;
import com.topic.parserAdapter.model.Topic;
import com.topic.parserAdapter.model.TopicType;

@At("/service")
@InjectName
@IocBean
public class TopicAdapter {
	@Inject
	private TopicTypeDao topicTypeDao;

	/**
	 * 根据用户id查询文档信接口
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
		int code = 0; // 状态码：0失败、1成功
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
			if (doc.getDocType() != null) {
				cri.where().andEquals("doc_type", doc.getDocType());
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
			code = 1;
			msg = "获取文档信息成功";
			List<Map<String, Object>> ml = new ArrayList<Map<String, Object>>();
			for (Document d : docList) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("docId", d.getDocId());
				m.put("fileName", d.getFileName());
				m.put("userId", d.getUserId());
				m.put("school", d.getSchool());
				m.put("className", d.getClassName());
				m.put("docType", d.getDocType());
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
	 * 查询文档内容接口
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
		int code = 0; // 状态码：0失败、1成功
		String msg = "获取文档内容失败";
		List<TopicType> ttList = null;
		Map<String, Object> m = new HashMap<String, Object>();
		List<Topic> tList = null;
		if (topic != null && topic.getDocId() != null) {
			m.put("docId", topic.getDocId());
			System.out.println("用户查询文档ID=【" + topic.getDocId() + "】的题目内容");
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
			code = 1;
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
						mt.put("content",
								t.getContent() // 回显替换IP地址
										.replace("${server}",
												IPUtil.getServerAddr(req)));
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

	// ///////////////////获取试卷答题结果接口(2014/11/26)//////////////////////
	/**
	 * 保存试卷答题结果接口
	 * 
	 * @param sh
	 * @param errCtx
	 * @return
	 */
	@At("/topic/putSheetResult")
	@Ok("json:{quoteName:true, ignoreNull:true}")
	@Fail("http:500")
	public Map<String, Object> putSheetResult(@Param("..") Sheet sh,
			AdaptorErrorContext errCtx, HttpServletRequest req) {

		if (errCtx != null) {
			System.out.println("保存试卷答题结果失败：" + errCtx.getErrors()[0]);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		int code = 0; // 状态码：0失败、1新增、2更新
		String msg = "保存试卷答题结果失败";
		Sheet re = null;
		if (sh.getState() == null || sh.getState().length() != 1) {
			System.out.println("请传送合法的【试卷状态】");
			msg = "【试卷状态】保留一位整数";
		} else if (sh.getDocId() == null || sh.getStuId() == null) {
			System.out.println("请传送合法的【学生ID】和【文档ID】");
			msg = "【学生ID】和【文档ID】不能为空";
		} else {
			// 检查是否存在该份试卷
			Sheet old = topicTypeDao.findByCondition(
					Sheet.class,
					Cnd.where("stuId", "=", sh.getStuId()).and("docId", "=",
							sh.getDocId()));
			if (old == null) { // 新增
				sh.setCommitTime(new Date());
				re = topicTypeDao.save(sh);
				msg = "保存试卷答题结果成功";
				code = 1;
			} else { // 更新
				re = old;
				old.setCommitTime(new Date());
				old.setState(sh.getState());
				old.setContent(sh.getContent());
				old.setStuId(sh.getStuId());
				old.setDocId(sh.getDocId());
				topicTypeDao.update(old);
				msg = "更新试卷答题结果成功";
				code = 2;
			}
		}
		m.put("code", code);
		m.put("msg", msg);
		m.put("obj", re == null ? "" : re.getId());
		return m;
	}

	/**
	 * 查询试卷答题结果接口
	 * 
	 * @param sh
	 * @param errCtx
	 * @return
	 */
	@At("/topic/getSheetResult")
	@Ok("json:{quoteName:true, ignoreNull:true}")
	@Fail("http:500")
	public Map<String, Object> getSheetResult(@Param("..") Sheet sh,
			AdaptorErrorContext errCtx, HttpServletRequest req) {

		if (errCtx != null) {
			System.out.println("查询试卷答题结果出错：" + errCtx.getErrors()[0]);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		int code = 0; // 状态码：0失败、1成功、2空数组
		String msg = "查询试卷答题结果失败";
		Object re = Collections.EMPTY_LIST;
		if (sh.getStuId() == null) {
			System.out.println("请传送合法的【学生编号】");
			msg = "【学生编号】不能为空";
		} else {
			Cnd c = Cnd.where("stuId", "=", sh.getStuId());
			//文档查找
			if (sh.getDocId() != null) {
				c = c.and("docId", "=", sh.getDocId());
			}//日期范围查找
			if(!Strings.isEmpty(sh.getStartDate())){
				c.and("commitTime", ">=", sh.getStartDate());
			}
			if(!Strings.isEmpty(sh.getEndDate())){
				c.and("commitTime", "<=", sh.getEndDate());
			}
			re = topicTypeDao.search(Sheet.class, c);

			if (re == null) {
				re = Collections.EMPTY_LIST;
				msg = "查询试卷答题结果为空";
				code = 2;
			}else{
				msg = "查询试卷答题结果成功";
				code = 1;
			}
		}
		m.put("code", code);
		m.put("msg", msg);
		m.put("obj", re);
		return m;
	}
}
