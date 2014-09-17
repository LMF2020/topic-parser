<%@page import="com.sun.xml.internal.rngom.ast.builder.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jsp/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎登陆演示demo首页面</title>
<%@include file="/common/jsp/common.jsp"%>
<style>
.lb{
	height: auto;
}
.box-row{
	width: 100%;
	margin:15px 0;
	padding: 0 20px;
}
.box{
	line-height: 250px;
	padding: 75px;
	border: 1px solid #ccc;
	cursor: pointer;
}
.box:hover{
	border:1px solid #0E9E3E;
	box-shadow:1px solid #ccc;
	zoom: 1;
	z-index: 999;
}
.box img{
	width:100px;
	height: 100px;
}
.box div{
	width: 100%;
	height: 40px;
	line-height: 40px;
	margin: 10px auto;
	text-align: center;
}
.user-info{
	width: 100%;
	height: auto;
	min-height: 40px;
	padding: 20px 10px;
	margin: 0;
}
.item {
	clear: both;
	margin: 15px 0;
	zoom: 1;
}
.item label {
	display: inline-block;
	float: left;
	padding-left:10px;
	margin-right: 15px;
	text-align: right;
	width: 100px;
	font-size: 14px;
	line-height: 30px;
	vertical-align: baseline;
	color:#666;
}
.item dd{
	width: 200px;
	padding: 5px;
	height: 18px;
	font-size: 14px;
	vertical-align: middle;
	color:#666;
}
.item dd a{
	color: #428bca;
	text-decoration: none;
}
h3{
	font-weight: 100;
	color: #666;
}
</style>
</head>
<body>
	<div class="wrapper">
		<%@include file="/common/jsp/header.jsp" %>
		<div class="content">
			<div class="navbar">
				<ul class="nav">
					<li class="active"><a href="#">首页</a></li>
					<li><a href="${ctx}/topic/${user.userId}/docList.htm">我的文档</a></li>
					<li><a href="${ctx}/topic/${user.userId}/upload.htm">上传文档</a></li>
				</ul>
			</div>
			<div class="page-content">
				<div class="row-container">
					 <div class="left-side rb-style w7 lb">
						<%--<div class="row-container">
							<div class="box-row">
								<div class="left-side w5 tac">
									<a class="box">
										<img src="${ctx}/common/images/document.png" />
										<div>我的文档</div>
									</a>
								</div>
								<div class="right-side w5 tac">
									<a class="box" href="#">
										我的文档
									</a>
								</div>
							</div>
						</div> --%>
					</div>
					<div class="right-side w3">
						<div class="user-info">
							<h3>用户基本信息</h3>
							<div class="item">
								<label>用户名称：</label> 
								<dd>
									<c:if test="${user != null}">
										${user.userId}
									</c:if>
								</dd>
							</div>
							<div class="item">
								<label>用户所在学校：</label> 
								<dd>
									<c:if test="${user != null}">
										${user.school}
									</c:if> 
								</dd>
							</div>
							<div class="item">
								<label>上传文档：</label> 
								<dd> 
								<c:if test="${docCount != null}">
										<a href="${ctx}/topic/${user.userId}/docList.htm">${docCount}</a>&nbsp;篇
									</c:if>  
								</dd>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/common/jsp/footer.jsp" %>
	</div>
</body>
</html>