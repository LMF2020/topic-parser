package com.topic.parserAdapter.adapter.module;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;
import org.nutz.trans.Molecule;
import org.nutz.trans.Trans;

import com.topic.parserAdapter.core.office.converter.Word2003ToHtmlConverter;
import com.topic.parserAdapter.core.office.parser.IdeaWordParser;
import com.topic.parserAdapter.core.util.MyFileUtils;
import com.topic.parserAdapter.dao.BasicDao;
import com.topic.parserAdapter.model.Document;
import com.topic.parserAdapter.model.Topic;

/**
 * 上传接口适配器
 * 提供给第三方调用文档上传、解析等适配器接口
 * @author Rayintee
 * 
 */
@At("/service")
@InjectName
@IocBean
public class DocumentAdapter {

	@Inject
	private BasicDao basicDao;//注入dao
	
	@Inject	
	private IdeaWordParser ideaWordParser;//注入word文档解析器
	
	/**
	 * 本地开发的上传服务，文件保存到/doc目录下等待处理
	 * @param tf
	 */
	@At("/document/upload")
	@Ok("json:{quoteName:true, ignoreNull:true}")
	@Fail("http:500")
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:myUpload" })
	public Map<String, Object> convert(@Param("fileProperty") Document docInfo, @Param("office") TempFile tf, 
			ServletContext sc, AdaptorErrorContext errCtx){
			Map<String, Object> m = new HashMap<String, Object>();
			
			if(errCtx != null){
				System.out.println("上传出错："+errCtx.getAdaptorErr().getMessage());
			}
			
			//解析文件流
			File tmpFile = tf.getFile();                 // 这个是保存的临时文件
		    FieldMeta meta = tf.getMeta();               // 这个原本的文件信息
		    String fileName = meta.getFileLocalName();   // 原始文件名称
		    long bytes = tmpFile.length();				 // 原始文件大小
		    String projectPath = sc.getRealPath("")+File.separatorChar;
		    try {
		    	//临时文件写入系统配置目录
		    	String filePath = projectPath+Word2003ToHtmlConverter.relativeFilePath+fileName;
		    	File tmp = new File(filePath);
		    	if(tmp.exists()) tmp.delete();
				Files.move(tmpFile, tmp);
			} catch (IOException e) {
				System.err.println("临时文件写入配置目录失败！");
			}
		    
		    //保存文档数据
		    docInfo.setFileName(fileName);
		    docInfo.setCreateTime(new Date());
		    docInfo.setFileSize(MyFileUtils.getFileSize(bytes));
		    docInfo.setCreateTimeStr(docInfo.getCreateTime());//设置字符串时间
		    docInfo = basicDao.save(docInfo);
		    
		    //处理|转换文档
		    final List<Topic> topics = ideaWordParser.getTopicList(sc, projectPath, fileName, docInfo);
		    //printDocList(topics); //打印输出
		    Molecule<Boolean> mol = new Molecule<Boolean>(){
				@Override
				public void run() {
					boolean flag = basicDao.saveBatch(topics);
					setObj(flag);
				}
		    };
		    Trans.exec(mol);
		    
		    int code = 1; //状态码：1失败、0成功
		    String msg = "上传题库失败";
		    if(mol.getObj()){
		    	code = 0;
		    	msg = "上传题库成功";
		    }
		    if(code == 1 && docInfo.getDocId()!=null){
		    	basicDao.delById(docInfo.getDocId().intValue(), Document.class);
		    }
		    //添加返回信息字段，转换成json返回给客户端
		    Map<String, Object> mm = new HashMap<String, Object>();
		    mm.put("docId", docInfo.getDocId());
		    mm.put("fileName", docInfo.getFileName());
		    mm.put("userId", docInfo.getUserId());
		    mm.put("school", docInfo.getSchool());
		    mm.put("className", docInfo.getClassName());
		    mm.put("subject", docInfo.getSubject());
		    mm.put("hours", docInfo.getHours());
		    mm.put("fileSize", docInfo.getFileSize());
		    mm.put("createTimeStr", docInfo.getCreateTimeStr());
		    m.put("code", code);
		    m.put("msg", msg);
		    m.put("list", mm);
		    System.out.println("上传成功--->\n"+Json.toJson(m));
		    return m;
	}
}
