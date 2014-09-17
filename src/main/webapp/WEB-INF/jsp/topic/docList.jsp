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
	font-weight: 100;
}
.wrap-content{
	width: 100%;
	display: flex;
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
.wrap-item:hover{
	background: #E6E6E6;
	cursor: pointer;
}
tbody,tr,thead {
	width: 100%;
}
</style>
</head>
<body>
	<h3 class="title">题库列表页面</h3>
	
	<div class="navbar">
		<ul class="nav">
			<li><a href="${ctx}/topic/index.htm">首页</a></li>
			<li class="active"><a href="#">题库列表</a></li>
			<li><a href="${ctx}/topic/upload.htm">上传题库</a></li>
			<li><a href="${ctx}/topic/query.htm">题目查询</a></li>
		</ul>
	</div>
	
	<div class="content">
		<div class="wrap">
			<table class="wrap-header">
				<tr>
					<th style="width: 10%;border-left:none;">ID</th>
					<th style="width: 10%;">科目</th>
					<th style="width: 10%;">题型</th>
					<th style="width: 25%;">题目内容</th>
					<th style="width: 20%;">答案</th>
					<th style="width: 10%;">得分</th>
					<th style="width: 15%;border-right:none;">提交时间</th>
				</tr>
			</table>
			<table class="wrap-content">
				<c:choose>
					<c:when test="${topicList eq null}">
						<tr>
							<td colspan="7" style="width:100%;text-align:center;">暂时没有题库信息，请上传</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="topic" items="${topicList}">
							<tr class="wrap-item">
								<td style="width: 10%;">${topic.id}</td>
								<td style="width: 10%;">${topic.subjectName}</td>
								<td style="width: 10%;">${topic.catalogName}</td>
								<td style="width: 25%;">${topic.content}</td>
								<td style="width: 20%;">${topic.answer}</td>
								<td style="width: 10%;">${topic.score}分</td>
								<td style="width: 15%;">${topic.createTime}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("tr.wrap-item").on("click", function(e){
				e.stopImmediatePropagation();//组织冒泡事件
				var $td = $(this).find("td:first-child"),
					url = "${ctx}/topic/detail/";
				var id = parseInt($td.text());
				console.log(url+id+".htm");
				window.location.href = url + id + ".htm";//跳转
			});
		});
	</script>
</body>
</html>