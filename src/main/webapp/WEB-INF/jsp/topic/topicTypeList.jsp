<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>演示系统-题目列表</title>
<%@include file="/common/jsp/common.jsp"%>
</head>
<body>
	<div class="wrapper">
		<%@include file="/common/jsp/header.jsp" %>
		<div class="content">
			<div class="navbar">
				<ul class="nav">
					<li><a href="${ctx}/topic/${user.userId}/docList.htm">我的文档</a></li>
					<li class="active"><a href="#">文档题型列表</a></li>
				</ul>
			</div>
			<div class="page-content">
				<div class="left-side rb-style w7 lb pr20">
					<table class="tab-header">
						<tr>
							<th style="width: 20%;border-left:none;">编号ID</th>
							<th style="width: 40%;">题型</th>
							<th style="width: 20%;">题数</th>
							<th style="width: 20%;border-right:none;">总分</th>
						</tr>
					</table>
					<table class="tab-content">
						<c:choose>
							<c:when test="${topicTypeList eq null}">
								<tr>
									<td colspan="6" style="width:100%;text-align:center;">
										<a href="#">此文档没有题目</a>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="topicType" items="${topicTypeList}">
									<tr class="tab-item">
										<td style="width: 20%;border-left:none;">${topicType.typeId}</td>
										<td style="width: 40%;">${topicType.topicType}</td>
										<td style="width: 20%;">共 ${topicType.typeCount} 题</td>
										<td style="width: 20%;">共 ${topicType.fullScore} 分</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
				<div class="right-side w3"></div>
			</div>
		</div>
		<%@include file="/common/jsp/footer.jsp" %>
	</div>
</body>
</html>