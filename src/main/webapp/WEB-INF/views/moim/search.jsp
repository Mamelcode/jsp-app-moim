<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>M.O.I.M</title>
<link rel="stylesheet" href="/resource/css/style2.css?${millis }" />
</head>
<body>
	<div class="root">
		<%-- 최상단 / 로그인 상태 출력 뷰--%>
		<c:choose>
			<c:when test="${sessionScope.logon }">
				<div
					style="display: flex; justify-content: space-between; width: 100%;">
					<div
						style="padding: 10px 20px; display: flex; align-items: center; gap: 10px">
						<a href="/">홈</a> <a href="/friend/manage">친구관리</a> <a
							href="/moim/create">모임개설</a>
					</div>
					<div
						style="padding: 10px 20px; display: flex; align-items: center; gap: 10px">
						<a href="/private/mypage"> <img
							src="${sessionScope.logonUser.avatarURL }" style="width: 36px" />
						</a> <b>${logonUser.name }</b>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div style="display: flex; justify-content: flex-end; width: 100%;">
					<div style="padding: 10px 20px;">
						<a href="/user/login">로그인</a>
					</div>
				</div>
			</c:otherwise>
		</c:choose>


		<%-- 메인 / 검색창 및 검색 결과 뷰 --%>
		<c:set var="joinedCate" value="${fn:join(paramValues.cate, '-')}" />
		<div style="flex: 1; min-width: 95vw; margin-top: 20px" class="block">
			<form class="block-row" style="align-items: center;" action="/moim/search">
				<div style="flex: 1" class="block-row">
					<c:forTokens items="스포츠,문화,학습,봉사,사교,게임,기타" delims="," var="one"
						varStatus="vs">
						<div>
							<input name="cate" 
								type="checkbox" value="${one }" id="cate${vs.count }" ${fn:contains(joinedCate, one) ? 'checked' : ''} /><label
								for="cate${vs.count }">${one }</label>
						</div>
						
					</c:forTokens>
				</div>
				<div style="" class="block">
					<input type="text" style="flex: 1" />
				</div>
				<div style="" class="block">
					<button type="submit" style="flex: 1">검색</button>
				</div>
			</form>
			<div style="flex: 1" class="block-row">
			<c:forEach items="${category}" var="moim">
				<div class="moim-detail-card block" style="margin-left: 5px;" onclick="location.href='/moim/detail?id=${moim.id}'">
					<div>
						<span style="color: blue">[${moim.type eq 'public' ? '공개' : '비공개'}/${moim.cate }]</span>&nbsp;
						<c:choose>
							<c:when test="${fn:length(moim.event) gt 16}">
									${fn:substring(moim.event, 0,16) }...
								</c:when>
							<c:otherwise>
									${moim.event }
								</c:otherwise>
						</c:choose>
						(${moim.currentPerson }/${moim.maxPerson })
					</div>
					<div style="text-align: left">
						# 일정 : <span><fmt:formatDate value="${moim.beginDate }"
								pattern="yyyy.MM.dd HH:mm" /></span> ~ <span><fmt:formatDate
								value="${moim.endDate }" pattern="HH:mm" /></span>

					</div>
					<div style="text-align: left">
						# 회비 : <span> <fmt:formatNumber value="${moim.finalCost }"
								pattern="#,###" />
						</span>
					</div>
					<div style="text-align: left">
						# 소개 : <span>${moim.description}</span>
					</div>
				</div>
			</c:forEach>
			</div>
			<%-- 페이징 번호 --%>
			<c:set var="currentPage" value="${empty param.page ? 1: param.page }"/>
			<c:set var="findCate" value="${fn:join(paramValues.cate, '&cate=')}" />
			<div style="display: flex; justify-content:center; ">
				<div>
					<c:choose>
						<c:when test="${existPrev }">
							<c:choose>
								<c:when test="${findCate != ''}">			
						 			<a href="/moim/search?page=${start - 1}&cate=${findCate}" style="margin-right: 10px;"> < </a>
						 		</c:when>
						 		<c:otherwise>
						 			<a href="/moim/search?page=${start - 1}" style="margin-right: 10px;"> < </a>
						 		</c:otherwise>
						 	</c:choose>
						</c:when>
						<c:otherwise>
							<a style="margin-right: 10px; color: #ccc;"> < </a>
						</c:otherwise>
					</c:choose>
				</div>
				<div>
					<c:forEach var="p" begin="${start}" end="${last}">
						<c:choose>
							<c:when test="${p eq currentPage}">
								<b style="color: red; margin: 0px 3px;">${p}</b>
							</c:when>
							<c:otherwise>		
								<c:choose>
									<c:when test="${findCate != ''}">			
							 			<a href="/moim/search?page=${p}&cate=${findCate}" style="margin: 0px 3px;">${p }</a>
							 		</c:when>
							 		<c:otherwise>
							 			<a href="/moim/search?page=${p}" style="margin: 0px 3px;">${p }</a>
							 		</c:otherwise>
							 	</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
				<div>
					<c:choose>
						<c:when test="${existNext }">
							<c:choose>
								<c:when test="${findCate != ''}">			
						 			<a href="/moim/search?page=${last + 1}&cate=${findCate}" style="margin-left: 10px;"> > </a>
						 		</c:when>
						 		<c:otherwise>
						 			<a href="/moim/search?page=${last + 1}" style="margin-left: 10px;"> > </a>
						 		</c:otherwise>
						 	</c:choose>
						</c:when>
						<c:otherwise>
							<a style="margin-left: 10px; color: #ccc;"> > </a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</body>
</html>