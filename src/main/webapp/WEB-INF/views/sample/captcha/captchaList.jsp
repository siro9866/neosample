<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>capcha 샘플</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- 여기에 스크립트 추가 -->
<script src='https://www.google.com/recaptcha/api.js'></script>

</head>

<body>capchaList<br>

	<div class="g-recaptcha" data-sitekey="6LfIepsUAAAAAOS3th5Dz4H3PoFYiIpVBntv--P4"></div>
	<button id="test_btn">구글캡챠</button>

</body>

<script>
	$(function() {
		$("#test_btn").click(
			function() {
				$.ajax({
					url : '/sample/captcha/verifyRecaptcha.neo',
					type : 'post',
					data : {
						recaptcha : $("#g-recaptcha-response").val()
					},
					success : function(data) {
						switch (data) {
						case 0:
							alert("자동 가입 방지 봇 통과 저장시작");
							break;
						case 1:
							alert("자동 가입 방지 봇을 확인 한뒤 진행 해 주세요.");
							break;
						default:
							alert("자동 가입 방지 봇을 실행 하던 중 오류가 발생 했습니다. [Error bot Code : "+ Number(data) + "]");
							break;
						}
					}
				});
			});
	});
</script>


</html>
