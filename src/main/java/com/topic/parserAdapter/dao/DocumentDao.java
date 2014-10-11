package com.topic.parserAdapter.dao;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;

import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.Topic;

public class DocumentDao extends BasicDao {

	/**
	 * 删除文档信息、相应的题目内容
	 * @param docId
	 * @return boolean
	 */
	public boolean deleteDocInfo(Long docId){
		boolean flag = false;//默认的为false
		Condition cnd = Cnd.where("doc_id", "=", docId);
		int rtn = dao.clear(Topic.class, cnd);
		System.out.println("删除【docId="+docId+"】文档对应的题目信息，返回值rtn==" + rtn);
		if(rtn >= 0){
			if(rtn == 0) System.out.println("【docId="+docId+"】文档对应的题目信息为空");
			else System.out.println("删除【docId="+docId+"】文档对应的题目信息成功");
			
			int ret = dao.clear(Document.class, cnd);
			System.out.println("删除文档属性信息，返回值ret==" + ret);
			if(ret == 1){
				System.out.println("确认删除文档信息成功");
				flag = true;
			}
		}
		
		return flag;
	}
}
