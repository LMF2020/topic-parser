<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>演示系统-文档列表</title>
<%@include file="/common/jsp/common.jsp"%>
<style>
</style>
</head>
<body>
	<div class="wrapper">
		<%@include file="/common/jsp/header.jsp" %>
		<div class="content">
			<div class="navbar">
				<ul class="nav">
					<li><a href="${ctx}/sys/${user.userId}/home.htm">首页</a></li>
					<li class="active"><a href="#">我的文档</a></li>
					<li><a href="${ctx}/topic/${user.userId}/upload.htm">上传文档</a></li>
				</ul>
			</div>
			<div class="page-content">
				<table class="tab-header">
					<tr>
						<th style="width: 10%;border-left:none;">编号ID</th>
						<th style="width: 30%;">文档名称</th>
						<th style="width: 15%;">科目</th>
						<th style="width: 10%;">课时</th>
						<th style="width: 15%;">文档大小</th>
						<th style="width: 20%;border-right:none;">上传时间</th>
					</tr>
				</table>
				<table class="tab-content">
					<c:choose>
						<c:when test="${docList eq null}">
							<tr>
								<td colspan="6" style="width:100%;text-align:center;">
									<a href="${ctx}/topic/${user.userId}/upload.htm">您还未上传任何文档</a>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="doc" items="${docList}">
								<tr class="tab-item">
									<td style="width: 10%;border-left:none;">${doc.docId}</td>
									<td style="width: 30%;">${doc.fileName}</td>
									<td style="width: 15%;">${doc.subject}</td>
									<td style="width: 10%;">${doc.hours}</td>
									<td style="width: 15%;">${doc.fileSize}</td>
									<td style="width: 20%;border-right:none;">${doc.createTimeStr}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
		</div>
		<%@include file="/common/jsp/footer.jsp" %>
	</div>
	<script type="text/javascript">
		$(function(){
			$("tr.tab-item").on("click", function(e){
				e.stopImmediatePropagation();//组织冒泡事件
				var $td = $(this).find("td:first-child"),
					url = "${ctx}/topic/";
				var id = parseInt($td.text());
				console.log(url+id+".htm");
				window.location.href = url + id + "/topicTypeList.htm";//跳转
			});
		});
	</script>
</body>
</html>