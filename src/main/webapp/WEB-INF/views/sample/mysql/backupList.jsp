<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>Mysql 백업 복구</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

</head>

<body>
	<div id="wrap">
	Mysql 백업 복구<br><br>
		<!--	S:검색조건 -->
		<form id="frm" name="frm" method="post">
			<input type="text" name="searchValue">
		</form>	 
		<!--	S:검색조건 -->
		<br><br>		
		
		<div class="button_wrap">
			<button type="button" name="btnBackup">백업</button>
			<button type="button" name="btnRestore">복구</button>
		</div>
		
		
	</div>
</body>

<script>

var url_backupStart = "/sample/mysql/backupStart.neo";
var url_restoreStart = "/sample/mysql/restoreStart.neo";

$(function(){
	$("button[name=btnBackup]").on("click", function(){
		var goUrl = url_backupStart;
 		$.ajax({
			type:"post",
			async:true,
			url:goUrl,
			data:$("#frm").serialize(),
			dataType:"json",
			success:function(responseData){
				if(responseData.result.rCode == "0000"){
					alert("백업성공");
				}else{
					alert(responseData.result.rMsg);
					return;
				}	
			},
			error: function (jqXHR, exception){
				alert("["+jqXHR.status+"]오류입니다.\n"+exception);
				return;
			}
		});
	});
	$("button[name=btnRestore]").on("click", function(){
		var goUrl = url_restoreStart;
 		$.ajax({
			type:"post",
			async:true,
			url:goUrl,
			data:$("#frm").serialize(),
			dataType:"json",
			success:function(responseData){
				if(responseData.result.rCode == "0000"){
					alert("복구성공");
				}else{
					alert(responseData.result.rMsg);
					return;
				}	
			},
			error: function (jqXHR, exception){
				alert("["+jqXHR.status+"]오류입니다.\n"+exception);
				return;
			}
		});
	});
	
});

</script>


</html>
