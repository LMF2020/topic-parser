package com.topic.parserAdapter.adapter.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.VoidAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;

import com.topic.parserAdapter.adapter.util.FormFile;
import com.topic.parserAdapter.adapter.util.SocketHttpRequest;

/**
 * 采用Nutz的Http包模拟测试
 * @author Rayintee
 * @see 
 * 		文档上传接口--> 
 * 			http://localhost:8015/topic-parser/service/document/upload
 *      参数:
 *      	"userId":"用户ID",
 *			"school":"用户所在学校"
 *			"className":"文档内容所属年级",   --—1:一年级、2:二年级…以此类推
 *			"subject":"文档内容所属科目",  ---1:语文、2:数学、3:英语(暂时只有3 种)
 *			"hours":"文档内容所属课时" –1：第一课时、2:第二课时…以此类推
 */
@At("/test")
@AdaptBy(type=VoidAdaptor.class)
@IocBean
public class UploadAdapterTest {
	private final static String UPLOAD_URL = "http://localhost:8015/topic-parser/service/document/upload";
	private final static String USER_ID = "admin";//上传用户
	private final static String SCHOOL = "合肥市江淮小学";//学校
	private final static int CLASS_NAME = 2;//年级
	private final static int SUBJECT = 1;//科目
	private final static int HOURS = 1;//课时
	
	//上传文档接口
	public static boolean uploadDocTest(){
		File _file = new File("F:/temp/白板语文题库.doc");//获取上传文件对象 --课文.doc
		Map<String, String> params = new HashMap<String, String>();  
        params.put("userId", USER_ID);
        params.put("school", SCHOOL);
        params.put("className", String.valueOf(CLASS_NAME));
        params.put("subject", String.valueOf(SUBJECT));
        params.put("hours", String.valueOf(HOURS));
        FormFile[] files = {new FormFile(_file, "office", "application/msword")};
        try {  
            return SocketHttpRequest.post(UPLOAD_URL, params, files);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
	}
	
	public static void main(String[] args) {
		uploadDocTest();//测试上传接口
	}
}
