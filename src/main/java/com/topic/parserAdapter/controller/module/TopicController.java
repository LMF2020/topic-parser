package com.topic.parserAdapter.controller.module;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;

import com.topic.parserAdapter.dao.TopicTypeDao;
import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.Topic;
import com.topic.parserAdapter.model.TopicType;
import com.topic.parserAdapter.model.User;


/**
 * 内部测试页面用到controller
 * @author Rayintee
 *
 */
@At("/topic")
@IocBean
public class TopicController {
	@Inject
	protected TopicTypeDao topicTypeDao;
	
	/**
	 * 跳转到上传页面
	 */
	@At("/?/upload")
	@Ok("jsp:jsp.topic.upload")
	@Fail("http:404")
	public void toUploadPage(@Param("userId") String userId, HttpServletRequest req){
		System.out.println(userId + " is going upload-->");
		List<User> u = topicTypeDao.search(User.class, Cnd.where("user_id", "=", userId));
		if(u.size()>0){
			User uu = u.get(0);
			req.setAttribute("user", uu);
		}
	}
	
	/**
	 * 跳转到文档列表页面
	 */
	@At("/?/docList")
	@Ok("jsp:jsp.topic.docList")
	@Fail("http:404")
	public void listTopics(@Param("userId") String userId, HttpServletRequest req){
		System.out.println(userId);
		List<User> u = topicTypeDao.search(User.class, Cnd.where("user_id", "=", userId));
		if(u.size()>0) {
			HttpSession session = req.getSession();
			session.setAttribute("user", u.get(0));
			List<Document> docList = topicTypeDao.search(Document.class, Cnd.where("user_id", "=", userId));
			System.out.println("-->查询到用户【" + userId + "】共有【" + docList.size() + "】篇文档");
			if(docList.size()>0) {
				for(Document doc: docList){
					Timestamp ts = new Timestamp(doc.getCreateTime().getTime());
					String str = ts.toString();
					str = str.substring(0, str.indexOf("."));
					doc.setCreateTimeStr(str);
				}
				req.setAttribute("docList", docList);
			}
		}
	}
	
	/**
	 * 跳装到题型列表上
	 * @param docId
	 * @param req
	 * @param errCtx
	 */
	@At("/topicTypeList/?")
	@Ok("jsp:jsp.topic.topicTypeList")
	@Fail("http:500")
	public void toDetailPage(@Param("id") Long docId, HttpServletRequest req, AdaptorErrorContext errCtx){
		if(errCtx != null){
			System.out.println("跳转页面出错："+errCtx.getErrors()[0]);
		}
		System.out.println("id-->"+docId);
		String sql = "SELECT catalog as topicType,COUNT(catalog) as typeCount, fullScore,doc_id as docId from t_topic $condition GROUP BY catalog ORDER BY id ASC";
		String condition = "where doc_id=" + docId;
		List<TopicType> tl = topicTypeDao.queryByNativeSql(TopicType.class, sql, condition);
		if(tl.size()>0){
			req.setAttribute("topicTypeList", tl);
		}
	}
	
	@At("/detailOpen/?")
	@Ok("jsp:jsp.topic.detailOpen")
	@Fail("http:500")
	public void toDetailOpenPage(@Param("id") int id, HttpServletRequest req, AdaptorErrorContext errCtx){
		if(errCtx != null){
			System.out.println("跳转页面出错："+errCtx.getErrors()[0]);
		}
		System.out.println("id-->"+id);
		Topic topic = topicTypeDao.find(id, Topic.class);//根据id查询
		Timestamp ts = new Timestamp(topic.getCreateTime().getTime());
		String str = ts.toString();
		System.out.println(str.substring(0, str.indexOf(".")));
		topic.setCreateTimeStr(str.substring(0, str.indexOf(".")));
		req.setAttribute("topic", topic);
	}
	
	@At("/query")
	@Ok("jsp:jsp.topic.query")
	@Fail("http:404")
	public void toQueryPage(){
	}
}
