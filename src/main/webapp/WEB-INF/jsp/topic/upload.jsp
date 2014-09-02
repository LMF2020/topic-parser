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
input[type="text"]{
	margin: 0;
	height: 30px;
	width: 246px;
	padding: 0 5px;
	line-height: 30px;
}
input[type="button"]{
	height: 34px;
	width: 100px;
	background: #008BF5;
	border-color: #008BF5;
	box-shadow: none;
	color: white;
	border-style: none;
	cursor: pointer;
}
.oneRow{
	width: 100%;
	height: 40px;
	padding: 5px 0;
}
.oneRow span{
	float:left;
	padding: 10px 0;
	width:60px;
	text-align: right;
	padding-right: 15px;
}
.oneRow select {
	height: 34px;
	width: 260px;
	padding: 0 5px;
}
.oneRow button{
	margin: 10px;
	height: 35px;
	width: 120px;
}
.submitBtn{
	color: #fff;
	background-color: #428bca;
	border: 1px solid #357ebd;
	cursor: pointer;
	margin-left: 70px !important;
}
.restBtn{
	background-color: #EBEBEB;
	border: 1px solid #E1E1E1;
	cursor: pointer;
}
.show{
	display: block !important;
}
.loading{
	position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 1000;
    background: rgba(255, 255, 255, 0.7);
    display: none;
}
.loading-logo{
	z-index:1001;
	position: relative;
	height: 150px;
	width: 150px;
	margin: 200px auto;
	background: url("${ctx}/common/images/loading.gif") no-repeat;
	background-size: 100% 100%; 
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
		<form method="post" enctype="multipart/form-data" id="fileUpload">
			<input type="hidden" name="docId" id="docId" />
			<div class="oneRow">
				<span>上传文件</span>
				<input type="file" name="office" id="office">
				<input type="text" name="path" id="path" readonly>
				<input type="button" id="browser" value="浏览..."/>
			</div>
			<div class="oneRow">
				<span>上传者ID</span>
				<input type="text" name="uuid" id="uuid" value="teacher_01"/>
			</div>
			<div class="oneRow">
				<span>课 时</span>
				<input type="text" name="hours" id="hours" value="1" />
			</div>
			<div class="oneRow">
				<span>年 级</span>
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
				<span>科 目</span>
				<select name="subject" id="subject">
					<option value="0">语文</option>
					<option value="1">其他</option>
				</select>
			</div>
			<div class="oneRow">
				<button type="button" class="submitBtn">提交</button>
				<button type="reset" class="restBtn">重置</button>
			</div>
		</form>
	</div>
	
	<div class="loading" id="loading"><div class="loading-logo"></div></div>
	
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
			
			//表单提交前验证
			$(".submitBtn").on("click", function(e){
				e.stopImmediatePropagation();//组织冒泡事件
				var path = $("#path").val();
				if(path== "" || path == null || path == undefined){
					alert("上传文件路径为空");
					return false;
				}
				
				//定义FormData对象
				var data = new FormData(), 
					params = $('#fileUpload').serializeArray(), 
					obj=new Object();
				$.each(params, function(i,v){
					if(!(v.name in obj)){
						obj[v.name]= v.value;
                    }
				});
				data.append('office',document.getElementById('office').files[0]);
				data.append('fileProperty', JSON.stringify(obj));
				//ajax上传
				$.ajax({
				    url: "http://localhost:8015/topic-parser/officeCenter/service/upload",
				    type: 'post',
				    data: data,
				    cache: false,
    				contentType:false,
				    timeout: 10*1000,//超时/ms
				    dataType: 'json',
				    processData: false,
				    beforeSend: function () {
				    	$("#loading").addClass("show");
                	}
                }).done(function(data){
                	var obj = $.parseJSON(data);
                	alert(obj.MSG);
                	return false;
                }).fail(function () {
                    alert("ajax请求失败");
                    return false;
                }).always(function( data, textStatus,jqXHR ){
                	setTimeout(function(){//做一下延迟，为了演示
                		$("#loading").removeClass("show");
                	},300);
                });
				return false;
			});
		});
	</script>
</body>
</html>