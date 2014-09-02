<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题库列表</title>
<%@include file="/common/jsp/common.jsp"%>
<style>
.wrap{
	width: 100%;
}
.wrap-header{
	background: #F7F7F7;
	border-radius: 2px;
	border: 1px solid #D2D2D2;
	color: #888;
	height: 41px;
	width: 100%;
}
tr{display: flex;}
th{
	height: 41px;
	line-height: 41px;
	border-left: 1px solid #fff;
	border-right: 1px solid #e5e5e5;
	float: left;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	/* text-indent: 10px; */
	font-weight: 100;
}
.wrap-content{
	width: 100%;
}
td{
	height: 38px;
	float: left;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	text-align: center;
	line-height: 38px;
}
.wrap-item{
	border-bottom: 1px solid #D2D2D2;
	zoom:1;
}
</style>
</head>
<body>
	<h3 class="title">题库接口页面</h3>
	
	<div class="navbar">
		<ul class="nav">
			<li class="active"><a href="#">题库接口</a></li>
			<li><a href="${ctx}/topic/upload">上传题库</a></li>
		</ul>
	</div>
	
	<div class="content">
		<div class="wrap">
			<table class="wrap-header">
				<tr>
					<th style="width: 10%;">ID</th>
					<th style="width: 10%;">科目</th>
					<th style="width: 10%;">题型</th>
					<th style="width: 25%;">题目内容</th>
					<th style="width: 20%;">答案</th>
					<th style="width: 10%;">得分</th>
					<th style="width: 15%;border-right:none;">提交时间</th>
				</tr>
			</table>
			<table class="wrap-content">
				<c:forEach var="list" items="${topicList}">
					<tr class="wrap-item">
						<td style="width: 10%;">${list.id}</td>
						<td style="width: 10%;">${list.catalog}</td>
						<td style="width: 10%;">${list.}</td>
						<td style="width: 25%;">本文作者是 $squart $br</td>
						<td style="width: 20%;">朱自清</td>
						<td style="width: 10%;">1分</td>
						<td style="width: 15%;">2014-09-01 22:04:11</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>