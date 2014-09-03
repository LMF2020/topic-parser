<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详情页面</title>
<%@include file="/common/jsp/common.jsp"%>
<style>
.detail-wrap{
	width: 100%;
	border: 1px solid #ddd;
	border-radius:5px;
	-webkit-border-radius:5px;
	-moze-border-radius:5px;
}
.wrap-header{
	height: 35px;
	width: 100%;
	background: #F7F7F7;
	border-bottom: 1px solid #ddd;
	color: #888;
	text-align: center;
	border-radius:5px 5px 0 0;
	-webkit-border-radius:5px 5px 0 0;
	-moz-border-radius:5px 5px 0 0;
}
.wrap-header span{
	margin: 0 auto;
	font-size: 14px;
	height: 100%;
	line-height: 35px;
}
.wrap-content{
	width: 100%;
	heigth: auto;
	min-height: 10px;
}
.row{
	margin: 20px 0;
	padding:0;
	width: 100%;
	height: auto;
	min-height: 20px;
	display: flex;
}
.row-lef{
	float:left;
	width: 70%;
	border-right: 1px solid #ddd;
}
.row-right{
	float:right;
	width: 30%;
}
.col-content{
	text-align: center;
	margin: 0;
	padding: 0;
	width: 100%;
}
.empty{
	height: 35px;
}
.oneRow{
	width: 100%;
	display: flex;
	height: 35px;
	margin: 0 auto;
}
.oneRow span, .oneRow div{
	height: 100%;
	line-height: 35px;
}
.oneRow span{
	width: 100px;
	text-align: right;
}
.oneRow div{
	margin-left: 30px;
}
.code{
	border: 1px solid #ddd;
	width: 90%;
	margin: 20px auto;
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
}
.code div{
	width: 100%;
	height: 25px;
	font-size:12px;
	background: #ccc;
	color:#888;
	text-align: left;
	border-radius:5px 5px 0 0;
	-webkit-border-radius:5px 5px 0 0;
	-moz-border-radius:5px 5px 0 0;
	line-height: 25px;
}
.code p{
	height: auto;
	min-height: 50px;
	text-align: center;
	font-size:12px;
	padding: 10px 30px;
	word-wrap: break-word;
}
</style>
</head>
<body>
	<c:if test="${!(topic eq null)}">
		<c:set var="cnt" value="${fn:replace(topic.content, '<br>', '&lt;br/&gt;')}" />
		<c:set var="content" value="${fn:replace(cnt, '<br/>', '&lt;br/&gt;')}" />
	</c:if>
	
	
	<h3 class="title">题目详情页面</h3>
	
	<div class="navbar">
		<ul class="nav">
			<li class="active"><a href="#">题目详情</a></li>
		</ul>
	</div>
	
	<div class="content">
		<div class="detail-wrap">
			<div class="wrap-header">
				<span>题目属性信息</span>
			</div>
			<div class="wrap-content">
				<div class="row">
					<c:choose>
						<c:when test="${topic eq null}">
							<div class="empty">题库中没有查询到该题目的详情信息，请刷新题库列表</div>
						</c:when>
						<c:otherwise>
							<div class="row-lef">
								<div class="code">
									<div>&nbsp;&nbsp;题目内容</div>
									<p>${content}</p>
								</div>
								
								<div class="col-content">
									<div class="code">
										<div>&nbsp;&nbsp;题目答案</div>
										<p>${topic.answer}</p>
									</div>
									
									<div class="code">
										<div>&nbsp;&nbsp;图片路径</div>
										<p>${topic.imgUrl}</p>
									</div>
								</div>
							</div>
							<div class="row-right">
								<div class="col-content">
									<div class="oneRow">
										<span>题目ID:</span>
										<div>${topic.id}</div>
									</div>
									<div class="oneRow">
										<span>小题编号:</span>
										<div>${topic.lowNum}</div>
									</div>
									<div class="oneRow">
										<span>题 型:</span>
										<div>题型${topic.catalog}</div>
									</div>
									<div class="oneRow">
										<span>分 值:</span>
										<div>${topic.score} 分</div>
									</div>
									<div class="oneRow">
										<span>所属文档ID:</span>
										<div>${topic.docId}</div>
									</div>
									<div class="oneRow">
										<span>上传用户ID:</span>
										<div>${topic.userId}</div>
									</div>
									<div class="oneRow">
										<span>所属年级:</span>
										<div>${topic.className}(年级)</div>
									</div>
									<div class="oneRow">
										<span>课时:</span>
										<div>第 ${topic.hours} 课时</div>
									</div>
									<div class="oneRow">
										<span>科目名称:</span>
										<div>科目${topic.subject}</div>
									</div>
									<div class="oneRow">
										<span>上传时间:</span>
										<div>${topic.createTimeStr}</div>
									</div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</body>
</html>