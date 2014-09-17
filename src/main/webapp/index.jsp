<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>接口规范页面</title>
<%@include file="/common/jsp/common.jsp"%>
</head>
<body>
	<div class="content">
		<p>这只是个开始...</p>
		
		<p>功能清单：</p>
		
		<p>
			实现一：word题库上传、解析、入库接口；<br/>
			地址：http://localhost:8015/topic-parser/officeCenter/service/upload;<br/>
			参数：office --上传文件<br/>
				  uuid --上传用户ID<br/>
				  hours --课时<br/>
				  className --年级<br/>
				  subject --科目<br/>
			返回值：{String} {"CODE":"0/1","MSG":"上传题库成功/失败"}
		</p>
		
		<p>
			实现二：根据用户ID、题型、课时、年级、科目等相关关键字进行模糊查询或者精确查询接口；<br>
			地址：http://localhost:8015/topic-parser/officeCenter/service/queryTopics;<br/>
			参数： userId --上传用户ID<br/>
				  hours --课时<br/>
				  subject --科目<br/>
				  catalog --题型<br/>
				  className --年级<br/>
			返回值：{String} {"CODE":"0/1","MSG":"上传题库成功/失败", "LIST":[{},{}...]}<br/>
		</p>
	</div>
</body>
</html>