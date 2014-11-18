package com.topic.parserAdapter.controller.module;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
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
	@At("/?/topicTypeList")
	@Ok("jsp:jsp.topic.topicTypeList")
	@Fail("http:500")
	public void toDetailPage(@Param("id") Long docId, HttpServletRequest req, AdaptorErrorContext errCtx){
		if(errCtx != null){
			System.out.println("跳转页面出错："+errCtx.getErrors()[0]);
		}
		System.out.println("id-->"+docId);
		String sql = "SELECT catalog as topicType,title,COUNT(catalog) as typeCount, fullScore,doc_id as docId from t_topic $condition GROUP BY catalog ORDER BY id ASC";
		String condition = "where doc_id=" + docId;
		List<TopicType> tl = topicTypeDao.queryByNativeSql(TopicType.class, sql, condition);
		if(tl.size()>0){
			req.setAttribute("topicTypeList", tl);
		}
	}
	
	/**
	 * 跳转到页面题库列表
	 * @param docId
	 * @param topicTypeNum
	 * @param req
	 * @param errCtx
	 */
	@At("/?/?/topicList")
	@Ok("jsp:jsp.topic.topicList")
	@Fail("http:500")
	public void toDetailOpenPage(@Param("docId") Long docId, @Param("topicTypeNum") int topicTypeNum, HttpServletRequest req, AdaptorErrorContext errCtx){
		if(errCtx != null){
			System.out.println("跳转页面出错："+errCtx.getErrors()[0]);
		}
		System.out.println("id-->" + docId + "=======topicTypeNum--->" + topicTypeNum);
		List<Topic> topics = topicTypeDao.search(Topic.class, Cnd.where("doc_id", "=", docId).and("catalog", "=", topicTypeNum));
		System.out.println("该文档题型【" + topicTypeNum + "】共有【" + topics.size() + "】道题");
		if(topics.size()>0){
			for(Topic t: topics){
				Timestamp ts = new Timestamp(t.getCreateTime().getTime());
				String str = ts.toString();
				System.out.println(str.substring(0, str.indexOf(".")));
				t.setCreateTimeStr(str.substring(0, str.indexOf(".")));
				int catalog = Integer.parseInt(t.getCatalog());
				t.setCatalogName(catalog);
			}
			
			req.setAttribute("topicList", topics);
		}
		req.setAttribute("docId", docId);
	}
	
	@At("/?/?/?/detail")
	@Ok("jsp:jsp.topic.detail")
	@Fail("http:500")
	public void toDetails(@Param("docId") Long docId, @Param("cataLog") int cataLog, @Param("id") int id, HttpServletRequest req, AdaptorErrorContext errCtx){
		if(errCtx != null){
			System.out.println("跳转页面出错："+errCtx.getErrors()[0]);
		}
		System.out.println("docId-->" + docId + "catalog-->" + cataLog +"id-->" + id);
		Condition cnd = null;
		if(id ==0){
			System.out.println("根据题型和文档id查询-->");
			cnd = Cnd.where("doc_id", "=", docId).and("catalog", "=", cataLog);;
		} else {
			System.out.println("直接根据题目id查询-->");
			cnd = Cnd.where("id", "=", id);
		}
		System.out.println("查询题型【"+ cataLog +"】下所有题目详细信息");
		List<Topic> topics = topicTypeDao.search(Topic.class, cnd);
		if(topics.size()>0){
			for(Topic topic:topics){
				Timestamp ts = new Timestamp(topic.getCreateTime().getTime());
				String str = ts.toString();
				System.out.println(str.substring(0, str.indexOf(".")));
				topic.setCreateTimeStr(str.substring(0, str.indexOf(".")));
				int catalog = Integer.parseInt(topic.getCatalog());
				topic.setCatalogName(catalog);
			}
			req.setAttribute("topicList", topics);
		}
		req.setAttribute("docId", docId);
		req.setAttribute("catalog", cataLog);
	}
	
	@At("/query")
	@Ok("jsp:jsp.topic.query")
	@Fail("http:404")
	public void toQueryPage(){
	}
}
