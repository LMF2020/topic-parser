<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jsp/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>演示系统-登录界面</title>
<%@include file="/common/jsp/common.jsp"%>
<style>
h1 {
	color: #494949;
	display: block;
	font-size: 25px;
	font-weight: bold;
	line-height: 1.1;
	margin: 0;
	padding: 0 0 30px;
	word-wrap: break-word;
}
.article {
	float: left;
	width: 520px;
	border-right: 1px solid #ddd;
	height: auto;
	min-height: 255px;
}
.aside {
	float: right;
	width: 390px;
	color: #666;
}
ol, ul {
	list-style: none;
}
.aside li {
	padding-bottom: 20px;
}
.item {
	clear: both;
	margin: 15px 0;
	zoom: 1;
}
label {
	display: inline-block;
	float: left;
	margin-right: 15px;
	text-align: right;
	width: 60px;
	font-size: 14px;
	line-height: 30px;
	vertical-align: baseline;
}
.item-captcha input:focus, .basic-input:focus {
	border: 1px solid #a9a9a9;
}
.input-active{
	color: rgb(0, 0, 0) !important;
}
.item-captcha input, .basic-input {
	width: 200px;
	padding: 5px;
	height: 18px;
	font-size: 14px;
	vertical-align: middle;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	border-radius: 3px;
	border: 1px solid #c9c9c9;
	color: rgb(204, 204, 204);
}
.remember {
	cursor: pointer;
	font-size: 12px;
	display: inline;
	width: auto;
	text-align: left;
	float: none;
	margin: 0;
	color: #666;
}
.btn-submit {
	cursor: pointer;
	color: #ffffff;
	background: #3fa156;
	border: 1px solid #528641;
	font-size: 14px;
	font-weight: bold;
	padding: 6px 26px;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
}
.err{
	padding-left: 70px;
	color: red;
	font-size: 12px;
}
</style>
</head>
<body>
	<div class="wrapper">
		<div class="header">
			<a href="" class="logo">登录演示系统</a>
		</div>
		<div class="content">
			<h1>登录演示系统</h1>
			<div class="article">
				<c:if test="${msg != null}">
					<dd class="err">${msg}</dd>
				</c:if>
				<form action="${ctx}/sys/loginCheck.htm" method="post" id="loginForm">
					<div class="item">
				        <label>帐号</label>
				        <input id="userId" name="userId" type="text" class="basic-input input-color" maxlength="50" 
				        	value="邮箱/手机号" tabindex="1">
				    </div>
				    <div class="item">
				        <label>密码</label>
				        <input id="userPwd" name="userPwd" type="text" class="basic-input input-color" 
				        	value="密码" maxlength="20" tabindex="2">
				    </div>
				    <div class="item">
				        <label>&nbsp;</label>
				        <p class="remember">
				            <input type="checkbox" id="remember" name="remember" tabindex="4">
				            <label for="remember" class="remember">下次自动登录</label>
				            | <a href="#">忘记密码了</a>
				        </p>
				    </div>
				    <div class="item">
				        <label>&nbsp;</label>
				        <input type="button" value="登录" id="loginBtn" name="loginBtn" class="btn-submit" 
				        	tabindex="5">
				    </div>
				</form>
			</div>
			<ul class="aside">
				<li>
					&gt;&nbsp;还没有系统帐号？
					<a rel="nofollow" href="#">立即注册</a>
				</li>
				<li>
					&gt;&nbsp;<a href="#">查看接口规范</a>
				</li>
			</ul>
		</div>
		<div class="footer">
			<span id="icp" class="fleft gray-link">
			    © 09.2014 online edu, all rights reserved
			</span>
			<span class="fright">
			    <a href="#">关于demo</a>
			    · <a href="#">联系我们</a>
			</span>
		</div>
	</div>
	<script>
		$(function(){
			//注册事件
			$(".basic-input").on("focus", function(e){
				e.stopImmediatePropagation();//组织冒泡事件
				var $val = $(this).val(), $pwd = $(this).attr("id")=="userPwd";
				if($pwd) $(this).attr("type", "password");
				if($val == "邮箱/手机号" || $val == "密码"){
					$(this).val("");
				}
				$(this).addClass("input-active");
			}).on("blur", function(e){
				e.stopImmediatePropagation();//组织冒泡事件
				var $val = $(this).val(), $pwd = $(this).attr("id")=="userPwd";
				if($val.trim() == ""){
					if($pwd) $(this).attr("type", "text");
					if($(this).attr("id") == "userPwd")
						$(this).val("").val("密码");
					else
						$(this).val("").val("邮箱/手机号");
					$(this).removeClass("input-active");
				}
			});
			
			$("#loginBtn").on("click", function(e){
				e.stopImmediatePropagation();//组织冒泡事件
				var $userId = $("#userId").val().trim(),$userPwd = $("#userPwd").val().trim();
				if($userId == ""|| $userPwd == "" || $userId == "邮箱/手机号" || $userPwd == "密码") {alert("用户名和密码不能为空");return false;}
				$("#loginForm").submit();//提交表单
				return false;
			});
		});
	</script>
</body>
</html>