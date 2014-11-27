package com.topic.parserAdapter.controller.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.http.sender.FilePostSender;
import org.nutz.json.Json;

import com.topic.parserAdapter.core.util.HttpClientTool;

/**
 * 接口测试用例
 * <h4>接口列表</h4><br>
 * <p>
 * <ul>
 * <li>{上传接口}.</li>
 * <li>{获取文档列表接口}.</li>
 * <li>{获取文档内容接口}.</li>
 * <li>{保存试卷答题结果接口}.</li>
 * <li>{查询试卷答题结果接口}.</li>
 * </ul>
 * <br>
 * @author jiangzx0526@gmail.com
 *
 */
public class TestAdapterClient {
	
	public static String uploadUrl = "http://localhost:8015/tiku/service/document/upload";
	public static String docsUrl =	"http://localhost:8015/tiku/service/topic/getDocList";
	public static String docContentUrl = "http://localhost:8015/tiku/service/topic/getTopicList";
	public static String putSheetResultUrl = "http://localhost:8015/tiku/service/topic/putSheetResult";
	public static String getSheetResultUrl = "http://localhost:8015/tiku/service/topic/getSheetResult";
	
	/**
	 * {上传接口}
	 * @param uploadUrl
	 */
	public void upload(String uploadUrl){
		 	Request req = Request.create(uploadUrl,METHOD.POST);
		 	File f = new File("D:/白板语文题库.doc");
	        //文件参数
		 	req.getParams().put("office", f);
	        //表单参数
	        Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", "admin");// 上传用户
			params.put("school", "合肥市江淮小学");// 学校
			params.put("className", 2);// 年级
			params.put("subject", 1);// 科目
			params.put("hours", 1);// 课时
			params.put("docType", 2); //情景类型
			req.getParams().put("fileProperty",Json.toJson(params));
	        FilePostSender sender = new FilePostSender(req);
	        Response resp = sender.send();
	        System.out.println(resp.getContent());
	}
	
	/**
	 * {获取文档列表接口}.
	 * @param docsUrl
	 */
	public void getDocs(String docsUrl){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", "admin");
		String cont = HttpClientTool.post(docsUrl, params);
		System.out.println(cont);
	}
	
	/**{获取文档内容接口}.
	 * @param docContentUrl
	 */
	public void getDocCont(String docContentUrl){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("docId", 1);
		String cont = HttpClientTool.post(docContentUrl, params);
		System.out.println(cont);
	}
	
	/**{保存试卷答题结果接口}.
	 * @param putSheetResultUrl
	 */
	public void putSheetResult(String putSheetResultUrl){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("docId", "55");
		params.put("stuId", "069084043");
		//params.put("stu_id", "079525535");
		params.put("state", 1);
		params.put("content", "{o:1,j:2,w:3,u:9}");
		String cont = HttpClientTool.post(putSheetResultUrl, params);
		System.out.println(cont);
	}
	
	/**{查询试卷答题结果接口}.
	 * @param getSheetResultUrl
	 */
	public void getSheetResult(String getSheetResultUrl){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("docId", "55");
		params.put("stuId", "069084043");
		params.put("startDate", "2014-11-25 00:00:00");
		params.put("endDate", "2014-11-29 24:59:59");
		String cont = HttpClientTool.post(getSheetResultUrl, params);
		System.out.println(cont);
	}
	
	public static void main(String[] args) {
		TestAdapterClient adt = new TestAdapterClient();
		//adt.upload(uploadUrl);
		//adt.getDocs(docsUrl);
		//adt.getDocCont(docContentUrl);
		//adt.putSheetResult(putSheetResultUrl);
		adt.getSheetResult(getSheetResultUrl);
	}
}
