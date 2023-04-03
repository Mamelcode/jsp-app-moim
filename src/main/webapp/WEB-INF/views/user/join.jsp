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
	<div class="root">
		<c:url value="/user/join-task" var="joinTo"/>
		<form action="${joinTo }" method="post" class="join-form">
			<h2>회원가입을 시작합니다</h2>
			<p class="join_service_text">
				회원가입을 하시면 <br /> 다양한 서비스를 이용하실 수 있습니다.
			</p>
			<div class="join_info_bg">
			<div class="info_box"">
				<h4 style="text-align: left; margin: 0.1em"><span>*</span> 사용할 아이디</h4>
				<input type="text" placeholder="아이디" name="id" />
			</div>
				<c:if test="${error eq 1}">
					<p class="error_text" style="color: red">영어/숫자로 이루어진 아이디를 만들어주세요!</p>
				</c:if>
				<c:if test="${error eq 4}">
					<p class="error_text" style="color: red">이미 존재하는 아이디 입니다!</p>
				</c:if>
			<div class="info_box">
				<h4 style="text-align: left; margin: 0.1em"><span>*</span> 사용할 비밀번호</h4>
				<input type="password" placeholder="비밀번호" name="pass" />
			</div>
				<c:if test="${error eq 2}">
					<p class="error_text" style="color: red">비밀번호는 4글자 이상입니다!</p>
				</c:if>
			<div class="info_box">
				<h4 style="text-align: left; margin: 0.1em"><span>*</span> 사용할 대표 닉네임</h4>
				<input type="text" placeholder="대표 닉네임" name="name" />
			</div>
				<c:if test="${error eq 3}">
					<p class="error_text" style="color: red">닉네임은 2글자 이상입니다!</p>
				</c:if>
			<div class="people_box">
				<h4><span>*</span> 사용할 아바타</h4>
				<div class="people_wrap">
					<c:forEach items="${avatars }" var="one">
					<div class="people_img">
						<label for="${one.id}">
							<img src="${one.url}"/>
						</label>
						<input type="radio" value="${one.id }" name="avatar"
								id="${one.id}"/>
					</div>
					</c:forEach>
				</div>
			</div>
			<h3>* 모든 정보는 필수 기입 정보 입니다.</h3>
			<div>
				<button type="submit" class="join-btn">다음</button>
			</div>
			</div>

		</form>
	</div>
</body>
</html>