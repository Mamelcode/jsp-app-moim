<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>M.O.I.M</title>
<link rel="stylesheet" href="/resource/css/style2.css?${millis }" />
</head>
<body>
	<div class="root">
		<%@ include file="/WEB-INF/views/common/top.jsp"%>
		<div class="moim-detail-container ">
			<div class="moim-detail-content">
				<div style="text-align: left">
					<div>
						<small><b style="color: deeppink">${moim.managerName }</b>
							님이 개설한 모임</small>
					</div>
					<h2 style="margin: 0.2em 0em;">${moim.event }</h2>
					<div style="justify-content: space-between;" class="block-row">
						<div>
							<a href="/moim/search"><span>전체</span></a> <span>&gt;</span>
						 	<a href="/moim/search?cate=${moim.cate }"><span>${moim.cate }</span></a> <span>|</span>
							<c:choose>
								<c:when test="${moim.type eq 'public' }">
									<span class="">공개</span>
								</c:when>
								<c:otherwise>
									<span class="">비공개</span>
								</c:otherwise>
							</c:choose>
							<span>|</span> <span>${moim.cate }</span> <span>|</span>
							<small>
							<fmt:formatDate value="${moim.beginDate }"
								pattern="yyyy.MM.dd (E)" />
							<span>|</span>
							<fmt:formatDate value="${moim.beginDate }" pattern="HH:mm" />
							~
							<fmt:formatDate value="${moim.endDate }" pattern="HH:mm" />
							</small>
						</div>
						<div>
							<c:if test="${moim.currentPerson ge moim.maxPerson }">
								<span style="color: red">마감된 모임</span>
							</c:if>
							${moim.currentPerson }/${moim.maxPerson }
						</div>
					</div>
					<div class="moim-detail-desc">${moim.description }</div>
				</div>
				<div class="block-row"
					style="justify-content: space-between; font-size: small; gap: 4px;">
					<div class="block-row">
						<div class="block-row" style="gap: 1px;">
							<img src="${moim.managerAvatarURL }"
								style="width: :24px; height: 24px;" /><span>${moim.managerName }</span>
						</div>
						<c:forEach var="vl" items="${attendances }">
							<div class="block-row" style="gap: 1px;">
								<img src="${vl.userAvatarURL }"
									style="width: :24px; height: 24px;" /><span>${vl.userName }</span>
							</div>
						</c:forEach>
					</div>
					<div>
						<c:if test="${sessionScope.logonUser.id ne moim.managerId}">
						<c:choose>
							<c:when test="${moim.currentPerson ge moim.maxPerson }">
								<a class="moim-join-bt">마감</a>
							</c:when>
							<c:when test="${status eq -1 }">
								<a href="/user/login" class="moim-join-bt">참가신청을 하기위해서는 로그인이 필요합니다.</a>
							</c:when>
							<c:when test="${status eq 0 }">
								<a href="/moim/join-task?id=${moim.id}&type=${moim.type}" class="moim-join-bt">참가신청</a>
							</c:when>
							<c:when test="${status eq 1 }">
								<a class="moim-join-bt">승인대기중</a>
							</c:when>
							<c:otherwise>
								<a class="moim-join-bt">참가취소</a>
							</c:otherwise>
						</c:choose>
						</c:if>
					</div>
				</div>
			</div>
			<c:choose>
			<c:when test="${empty commentList}">
				<h3>등록된 댓글이 없습니다.</h3>
			</c:when>
			<c:otherwise>
			<div class="comment_list">
			<c:forEach items="${commentList}" var="comment">
				<div class="comment_list_sub">
					<div>
						<c:choose>
							<c:when test="${comment.writer eq sessionScope.logonUser.id}">
								<p style="font-weight: 700;">${comment.writer}
								<span style="font-size: 12px; color: blue;">(나)</span></p>
							</c:when>
							<c:otherwise>
								<p style="font-weight: 700;">${comment.writer}</p>
							</c:otherwise>
						</c:choose>
					</div>
					<div>
						<p style="margin: 10px 0px;">${comment.ment}</p>
					</div>
					<div>
						<p style="font-size: 13px;"><fmt:formatDate value="${comment.writed }" pattern="yyyy/MM/dd"/></p>
					</div>
				</div>
			</c:forEach>
			</div>
			</c:otherwise>
			</c:choose>
			<c:choose>
			<c:when test="${empty sessionScope.logonUser}">
			<form class="comment_form" action="/moim/comment" method="post">
				<input type="hidden" name="commentUser" value="${sessionScope.logonUser.id}">
				<input type="hidden" name="moimId" value="${moim.id}">
				<textarea style="border: 1px solid #ccc;" class="comment_formta" 
				name="commentText" disabled="disabled"></textarea>
				<span>댓글을 작성하려면 <a href="/user/login" style="color: blue">로그인</a>을 해주세요.</span>
				<div class="comment_formbtbox">
					<button class="comment_formbt" type="submit" disabled="disabled">댓글작성</button>
				</div>
			</form>
			</c:when>
			<c:otherwise>
			<form class="comment_form" action="/moim/comment" method="post">
				<input type="hidden" name="commentUser" value="${sessionScope.logonUser.id}">
				<input type="hidden" name="moimId" value="${moim.id}">
				<textarea style="border: 1px solid #ccc;" class="comment_formta" name="commentText" placeholder="댓글을 작성해주세요."></textarea>
				<div class="comment_formbtbox">
					<button class="comment_formbt" type="submit">댓글작성</button>
				</div>
			</form>
			</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>