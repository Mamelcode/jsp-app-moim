<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>M.O.I.M</title>
<link rel="stylesheet" href="/resource/css/initial.css" />
<link rel="stylesheet" href="/resource/css/style.css?${millis }" />
</head>
<body>
	<div class="root login_area">
		<form action="/user/login-task" method="post" class="join-form">

			<h2>#로그인</h2>
			<div class="user_input">
			    <label>아이디</label>
				<input type="text" placeholder="아이디" name="id" value="${idsave }"
					class="join-input" required />
				<c:if test="${param.url != null}">
					<input type="hidden" name="url" value="${param.url}">
				</c:if>
			</div>
			<c:if test="${error eq 2}">
				<p class="error_text" style="color: red">존재하지않는 아이디 입니다!</p>
			</c:if>
			<div class="user_input">
			    <label>비밀번호</label>
				<input type="password" placeholder="비밀번호" name="pass" class="join-input" />
			</div>
			<c:if test="${error eq 1}">
				<p class="error_text" style="color: red">비밀번호가 일치하지 않습니다!</p>
			</c:if>
			<div>
				<p><input type="checkbox" name="idCookie" style="margin-right: 10px;">아이디 기억하기</p>
			</div>
			<div>
				<button type="submit" class="join-btn">다음</button>
			</div>
		</form>
		<p>
			계정이 없으신가요? <a href="/user/join">가입하기</a>
		</p>
	</div>
</body>
</html>