<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Sample</title>
	
<style>
	
	body{
		color:white;
		width: 100%;
		height: 100%;
		margin: auto;
		background-color: black;
	}
	
	#wrapper ul{margin: 30px;	padding:5px;	border: 5px dotted red;}
	#wrapper ul li{list-style-type: circle; margin-bottom: 10px;}
	
</style>
	
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	
</head>

<body>

	<div id="wrapper">
		<ul>
			<li id="/sample/mysql/mysqlList.neo">MySQL</li>
			<li id="/sample/mysql/backupList.neo">MySQL백업및복구</li>
		</ul>
	</div>
	
	<!-- REST FULL -->
	<div id="wrapper">
		<ul>
			<li id="/sample/restful/restfulList.neo">REST FUL</li>
			<li id="/sample/socket/socketList.neo">SOCKET</li>
			<li id="/sample/socket/broadsocket.neo">WEBSOCKET 채팅</li>
		</ul>
	</div>

	<div id="wrapper">
		<ul>
			<li id="/sample/etc/jsonJsp.neo">JSON JSP활용</li>
		</ul>
	</div>

	<div id="wrapper">
		<ul>
			<li id="/sample/captcha/captchaList.neo">구글 CAPCHA(ajax)</li>
			<li id="/sample/captcha/recaptchaList.neo">구글 CAPCHA(서브밋)</li>
			<li id="/sample/captcha/simpleCaptcha.neo">SIMPLE CAPCHA</li>
		</ul>
	</div>

	<div id="wrapper">
		<ul>
			<li id="/sample/api/juso/jusoAPI.neo">주소 API(팝업)</li>
			<li id="/sample/api/juso/jusoAPISearch.neo">주소 API(검색)</li>

			<li id="/sample/api/map/mapList.neo">지도</li>
		</ul>
	</div>

<script>

	$("ul li").on("click", function(){
		window.open($(this).attr("id"));
	});

</script>

</body>
</html>
