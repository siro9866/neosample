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
<title>SOCKET 샘플</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</head>

<body>SOCKET<br>


	
		<div class="row">
			<div class="col-lg-12 ">
				<div class="col-lg-6 col-md-6" >
					CLIENT<br>
					<div id="client" style="width:300px; height:200px; border: 2px solid;"></div>
				</div>
				<div class="col-lg-6 col-md-6">
					SERVER<br>
					<div id="server" style="width:300px; height:200px; border: 2px solid red;"></div>
				</div>
			</div>
			<form id="frm">
				<div class="col-lg-6 col-md-6">
					<input type="text" class="form-control input-lg" name="searchValue" value="${paramDto.searchValue }">
					<br><br><br>웹호출<br>
					<button type="button" name="socketOpen" class="btn btn-primary btn-lg btn-block"><i class="fa fa-search"></i>&nbsp;서버소켓오픈</button>
					<button type="button" name="socketClient" class="btn btn-primary btn-lg btn-block"><i class="fa fa-search"></i>&nbsp;클라이언트콜</button>
				</div>
			</form>
			<br><br><br>
			<div class="col-lg-6 col-md-6" id="responseData" style="border: 2px; border-color: blue">
			</div>
		</div>

</body>

<script>
	
	var url_socketOpen = "/sample/socket/socketOpen.neo";
	var url_socketClient = "/sample/socket/socketClient.neo";

	$(function() {
		
		// 소켓오픈
		$("button[name=socketOpen]").on("click", function(){
			var goUrl = url_socketOpen;
	 		$.ajax({
				type:"post",
				async:true,
				url:goUrl,
				data:$("#frm").serialize(),
				dataType:"json",
				success:function(data){
					if(data.rCode == "0000"){
						$("#server").append(data.result.sample_name);
					}else{
						alert("실패:\n"+data.rMsg);
						return;
					}	
				},
				error: function (jqXHR, exception){
					alert("["+jqXHR.status+"]오류입니다.\n"+exception);
					return;
				}
			});
		});
		
		$("button[name=socketClient]").on("click", function(){
			var goUrl = url_socketClient;
	 		$.ajax({
				type:"post",
				async:true,
				url:goUrl,
				data:$("#frm").serialize(),
				dataType:"json",
				success:function(data){
// 					alert(JSON.stringify(responseData));
// 					var str = "sample_id:"+responseData.sample_id + ",sample_name:"+responseData.sample_name + ",searchValue:"+responseData.searchValue;
// 					$("#responseData").html(str);
					
					if(data.rCode == "0000"){
// 						alert("요청완료");
						$("#client").append(data.result.sample_name);
					}else{
						alert(data.rMsg);
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
