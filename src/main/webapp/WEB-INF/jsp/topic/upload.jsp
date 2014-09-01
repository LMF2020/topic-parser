<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jsp/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传测试页面</title>
<%@include file="/common/jsp/common.jsp"%>
<style>
input[type="file"]{
	display:none;
}
.oneRow{
	width: 100%;
	height: 40px;
	padding: 5px 0;
}
.oneRow span{
	float:left;
	padding: 5px 0;
	width:60px;
	text-align: right;
	padding-right: 15px;
}
.oneRow select {
	width: 80px;
}
.oneRow button{
	margin: 5px 10px;
}
.submitBtn{
	color: #fff;
	background-color: #428bca;
	border: 1px solid #357ebd;
	border-radius:5px;
	width:50px;
	cursor: pointer;
}
.restBtn{
	
	background-color: #EBEBEB;
	border: 1px solid #E1E1E1;
	border-radius:5px;
	width:50px;
	cursor: pointer;
}
</style>
</head>
<body>
	<h3 class="title">题库上传页面</h3>
	
	<div class="navbar">
		<ul class="nav">
			<li><a href="${ctx}/topic/list">题库接口</a></li>
			<li class="active"><a href="#">上传题库</a></li>
		</ul>
	</div>
	
	<div class="content">
		<form action="http://localhost:8015/topic-parser/officeCenter/service/convert" 
		  target="file_upload" method="post" enctype="multipart/form-data">
			<input type="hidden" name="docId" id="docId" />
			<div class="oneRow">
				<span>上传文件</span>
				<input type="file" name="office" id="office">
				<input type="text" name="path" id="path" class="ipt">
				<input type="button" id="browser" value="浏览..."/>
			</div>
			<div class="oneRow">
				<span>上传者ID</span>
				<input type="text" name="uuid" id="uuid" value="teacher_01"/>
			</div>
			<div class="oneRow">
				<span>课时</span>
				<input type="text" name="hours" id="hours" />
			</div>
			<div class="oneRow">
				<span>年级</span>
				<select name="className" id="className">
					<option value="1">一年级</option>
					<option value="2">二年级</option>
					<option value="3">三年级</option>
					<option value="3">三年级</option>
					<option value="4">四年级</option>
					<option value="5">五年级</option>
					<option value="6">六年级</option>
				</select>
			</div>
			<div class="oneRow">
				<span>科目</span>
				<select name="subject" id="subject">
					<option value="0">语文</option>
					<option value="1">其他</option>
				</select>
			</div>
			<div class="oneRow">
				<button type="submit" class="submitBtn">提交</button>
				<button type="reset" class="restBtn">重置</button>
			</div>
		</form>
		<iframe name="file_upload" style="display:none"></iframe>
	</div>
	
	<script type="text/javascript">
		$(function(){
			//模拟按钮点击事件效果
			$("#browser").on("click", function(e){
				e.stopImmediatePropagation();//组织冒泡事件
				$("#office").click();//模拟点击事件
			});
			$("#office").on("change", function(e){
				var path = $(this).val().replace("C:\\fakepath\\", "");
				if(path.indexOf(".doc") != -1 || path.indexOf(".docx") != -1){
					$("#path").val(path);//赋值
				} else {
					alert("目前只支持word格式文档");
				}
			});
			
			
		});
	</script>
</body>
</html>