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
<title>REST FUL 샘플</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</head>

<body>REST FUL<br>


	<form id="frm" name="frm" method="get">
		<input type="hidden" name="sample_id" id="sample_id"/>
	
		<div class="row">
			<div class="col-lg-12 ">
				<div class="col-lg-6 col-md-6">
					<input type="text" class="form-control input-lg" name="searchValue" value="${paramDto.searchValue }">
					<br><br><br>웹호출<br>
					<button type="button" name="btnJsonSubmit" class="btn btn-primary btn-lg btn-block"><i class="fa fa-search"></i>&nbsp;JSON(서브밋 VO)</button>
					<button type="button" name="btnJsonAjax" class="btn btn-primary btn-lg btn-block"><i class="fa fa-search"></i>&nbsp;JSON(ajax VO)</button>
					<button type="button" name="btnJsonMap" class="btn btn-primary btn-lg btn-block"><i class="fa fa-search"></i>&nbsp;JSON(MAP)</button>
					<button type="button" name="btnXML" class="btn btn-primary btn-lg btn-block"><i class="fa fa-search"></i>&nbsp;XML</button>
					<button type="button" name="btnXMLAjax" class="btn btn-primary btn-lg btn-block"><i class="fa fa-search"></i>&nbsp;XML(ajax)</button>
					<br><br><br>서버호출<br>
					<button type="button" name="btnServerAjax" class="btn btn-primary btn-lg btn-block"><i class="fa fa-search"></i>&nbsp;서버(ajax)</button>
				</div>
				<br><br><br>
				<div class="col-lg-6 col-md-6" id="responseData" style="border: 2px; border-color: blue">
				</div>
			</div>
		</div>
	</form>	 

</body>

<script>
	
	var url_json = "/sample/restful/restfulJson.neo";
	var url_jsonMap = "/sample/restful/restfulJsonMap.neo";
	var url_xml = "/sample/restful/restfulXML.neo";
	var url_xml2 = "/sample/restful/restfulXML/1.neo";

	$(function() {
		
		// JSON VO 서브밋
		$("button[name=btnJsonSubmit]").on("click", function(){
			$("#frm").attr("action", url_json);
			$("#frm").submit();
		});
		
		// JSON VO AJAX
		$("button[name=btnJsonAjax]").on("click", function(){
			var goUrl = url_json;
	 		$.ajax({
				type:"post",
				async:true,
				url:goUrl,
				data:$("#frm").serialize(),
				dataType:"json",
				success:function(responseData){
					alert(JSON.stringify(responseData));
					var str = "sample_id:"+responseData.sample_id + ",sample_name:"+responseData.sample_name + ",searchValue:"+responseData.searchValue;
					$("#responseData").html(str);
				},
				error: function (jqXHR, exception){
					alert("["+jqXHR.status+"]오류입니다.\n"+exception);
					return;
				}
			});
		});
		
		// JSON Map
		$("button[name=btnJsonMap]").on("click", function(){
			var goUrl = url_jsonMap;
	 		$.ajax({
				type:"post",
				async:true,
				url:goUrl,
				data:$("#frm").serialize(),
				dataType:"json",
				success:function(responseData){
					if(responseData.rCode == "0000"){
						alert(JSON.stringify(responseData));
						var str = "sample_id:"+responseData.result.sample_id + ",sample_name:"+responseData.result.sample_name + ",searchValue:"+responseData.result.searchValue;
						$("#responseData").html(str);
					}else{
						alert(responseData.rMsg);
						return;
					}	
				},
				error: function (jqXHR, exception){
					alert("["+jqXHR.status+"]오류입니다.\n"+exception);
					return;
				}
			});
		});
		
		
		$("button[name=btnXML]").on("click", function(){
			$("#frm").attr("action", url_xml);
			$("#frm").submit();
		});
		
		$("button[name=btnXMLAjax]").on("click", function(){
			var goUrl = url_xml;
	 		$.ajax({
				type:"post",
				async:true,
				url:goUrl,
				data:$("#frm").serialize(),
				dataType:"xml",
				success:function(responseData){
					var view_text="";
					$(responseData).find('employee').each(function(){  // xml 문서 item 기준으로 분리후 반복
						var employee = $(this).attr('id'); 
						var firstName = $(this).find("firstName").text(); 
						var lastName = $(this).find("lastName").text(); 
						var email = $(this).find("email").text(); 

						view_text += employee + "," + firstName + "," + lastName + "," + email +"<br>"; 
					});
					$("#responseData").html(view_text);  // #id 에 view_text 삽입

				},
				error: function (jqXHR, exception){
					alert("["+jqXHR.status+"]오류입니다.\n"+exception);
					return;
				}
			});
		});
		
	});

	
	$("button[name=btnServerAjax]").on("click", function(){
		alert("RestfulCaller.java 실행하세요!!");
	});
</script>


</html>
