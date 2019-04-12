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
<title>주소API</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

</head>

<body>
	<div id="wrap">
		www.juso.go.kr API 이용<br><br>
		<!--	S:검색조건 -->
		<form name="frmXML" id="frmXML" method="post">
			<input type="hidden" name="currentPage" value="1"/>				<!-- 요청 변수 설정 (현재 페이지. currentPage : n > 0) -->
			<input type="hidden" name="countPerPage" value="10"/>				<!-- 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100) -->
			<input type="hidden" name="confmKey" value="${JUSO_API_KEY }"/>		<!-- 요청 변수 설정 (승인키) -->
			<input type="text" name="keyword" value="만리재옛길 36"/>						<!-- 요청 변수 설정 (키워드) -->
			<input type="button" id="btnJusoSearchXML" value="주소검색하기(XML)"/>
			<input type="button" id="btnJusoSearchXMLP" value="웹주소검색하기(XML)"/>
		</form>
		<br><br><br>
		<form name="frmJSON" id="frmJSON" method="post">
			<input type="hidden" name="currentPage" value="1"/>				<!-- 요청 변수 설정 (현재 페이지. currentPage : n > 0) -->
			<input type="hidden" name="countPerPage" value="10"/>				<!-- 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100) -->
			<input type="hidden" name="resultType" value="json"/>
			<input type="hidden" name="confmKey" value="${JUSO_API_KEY }"/>		<!-- 요청 변수 설정 (승인키) -->
			<input type="text" name="keyword" value="만리재옛길 36"/>						<!-- 요청 변수 설정 (키워드) -->
			<input type="button" id="btnJusoSearchJSON" value="주소검색하기(JSON)"/>
			<input type="button" id="btnJusoSearchJSONP" value="웹주소검색하기(JSON)"/>
		</form>
		<!--	S:검색조건 -->
		<br><br>		

		<div id="list"> <!-- 검색 결과 리스트 출력 영역 --> </div>
		
	</div>
</body>

<script>

$(function(){
	$("#btnJusoSearchXML").on("click", function(){
		// AJAX 주소 검색 요청
		$.ajax({
			url:"/sample/api/juso/getAddrApiXML.neo"						// 고객사 API 호출할 Controller URL
			,type:"post"
			,data:$("#frmXML").serialize() 								// 요청 변수 설정
			,dataType:"xml"												// 데이터 결과 : XML
			,success:function(xmlStr){									// xmlStr : 주소 검색 결과 XML 데이터
				$("#list").html("");									// 결과 출력 영역 초기화
				var errCode= $(xmlStr).find("errorCode").text();		// 응답코드
				var errDesc= $(xmlStr).find("errorMessage").text();		// 응답메시지
				if(errCode != "0"){ 									// 응답에러시 처리
					alert(errCode+"="+errDesc);
				}else{
					if(xmlStr!= null){
						makeList(xmlStr);								// 결과 XML 데이터 파싱 및 출력
					}
				}
			}
			,error: function(xhr,status, error){
				alert("["+xhr.status+"]오류입니다.\n"+error);		// AJAX 호출 에러
			}
		});
	});
	
	
	$("#btnJusoSearchXMLP").on("click", function(){
		// AJAX 주소 검색 요청
		$.ajax({
			url:"http://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"	// 주소검색 OPEN API URL
			,type:"post"
			,data:$("#frmXML").serialize() 								// 요청 변수 설정
			,dataType:"jsonp"											// 크로스도메인으로 인한 jsonp 이용, 검색결과형식 XML 
			,crossDomain:true
			,success:function(xmlStr){									// xmlStr : 주소 검색 결과 XML 데이터
				if(navigator.appName.indexOf("Microsoft") > -1){		// IE 환경에서 JSONP의 returnXml 결과 데이터 처리
					var xmlData= newActiveXObject("Microsoft.XMLDOM");
					xmlData.loadXML(xmlStr.returnXml);
				}else{													 // IE 이외 환경에서 처리
					var xmlData= xmlStr.returnXml;
				}
				$("#list").html("");									// 결과 출력 영역 초기화
				var errCode= $(xmlData).find("errorCode").text();
				var errDesc= $(xmlData).find("errorMessage").text();
				
				if(errCode != "0"){ 
					alert(errCode+"="+errDesc);
				}else{
					if(xmlStr!= null){
						makeList(xmlData);								// 결과 XML 데이터 파싱 및 출력
					}
				}
			}
			,error: function(xhr,status, error){
				alert("에러발생");										// AJAX 호출 에러
			}
		});
	});
	
	$("#btnJusoSearchJSON").on("click", function(){
		// AJAX 주소 검색 요청
		$.ajax({
			url:"/sample/api/juso/getAddrApiJSON.neo"						// 고객사 API 호출할 Controller URL
			,type:"post"
			,data:$("#frmJSON").serialize() 								// 요청 변수 설정
			,dataType:"json"											// 데이터 결과 : JSON
			,success:function(jsonStr){									// jsonStr : 주소 검색 결과 JSON 데이터
				$("#list").html("");									// 결과 출력 영역 초기화
				var errCode = jsonStr.resultJson.results.common.errorCode; 		// 응답코드
				var errDesc = jsonStr.resultJson.results.common.errorMessage;		// 응답메시지
				if(errCode != "0"){ 									// 응답에러시 처리
					alert(errCode+"="+errDesc);
				}else{
					if(jsonStr.resultJson!= null){
						makeListJson(jsonStr.resultJson);							// 결과 JSON 데이터 파싱 및 출력
					}
				}
			}
			,error: function(xhr,status, error){
				alert("["+xhr.status+"]오류입니다.\n"+error);		// AJAX 호출 에러
			}
		});
	});
	
	$("#btnJusoSearchJSONP").on("click", function(){
		// AJAX 주소 검색 요청
		$.ajax({
			 url :"http://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"  //인터넷망
			,type:"post"
			,data:$("#frmJSON").serialize()
			,dataType:"jsonp"
			,crossDomain:true
			,success:function(jsonStr){
				$("#list").html("");
				var errCode = jsonStr.results.common.errorCode;
				var errDesc = jsonStr.results.common.errorMessage;
				if(errCode != "0"){
					alert(errCode+"="+errDesc);
				}else{
					if(jsonStr != null){
						makeListJson(jsonStr);
					}
				}
			}
			,error: function(xhr,status, error){
				alert("에러발생");
			}
		});
	});
	
});

function makeList(xmlStr){
	var htmlStr = "";
	htmlStr += "<table>";
	// jquery를 이용한 XML 결과 데이터 파싱
	$(xmlStr).find("juso").each(function(){
		htmlStr += "<tr>";
		htmlStr += "<td>"+$(this).find('roadAddr').text() +"</td>";
		htmlStr += "<td>"+$(this).find('roadAddrPart1').text() +"</td>";
		htmlStr += "<td>"+$(this).find('roadAddrPart2').text() +"</td>";
		htmlStr += "<td>"+$(this).find('jibunAddr').text() +"</td>";
		htmlStr += "<td>"+$(this).find('engAddr').text() +"</td>";
		htmlStr += "<td>"+$(this).find('zipNo').text() +"</td>";
		htmlStr += "<td>"+$(this).find('admCd').text() +"</td>";
		htmlStr += "<td>"+$(this).find('rnMgtSn').text() +"</td>";
		htmlStr += "<td>"+$(this).find('bdMgtSn').text() +"</td>";
		htmlStr += "<td>"+$(this).find('detBdNmList').text() +"</td>";
		/** API 서비스 제공항목 확대 (2017.02) **/
		htmlStr += "<td>"+$(this).find('bdNm').text()+"</td>";
		htmlStr += "<td>"+$(this).find('bdKdcd').text()+"</td>";
		htmlStr += "<td>"+$(this).find('siNm').text()+"</td>";
		htmlStr += "<td>"+$(this).find('sggNm').text()+"</td>";
		htmlStr += "<td>"+$(this).find('emdNm').text()+"</td>";
		htmlStr += "<td>"+$(this).find('liNm').text()+"</td>";
		htmlStr += "<td>"+$(this).find('rn').text()+"</td>";
		htmlStr += "<td>"+$(this).find('udrtYn').text()+"</td>";
		htmlStr += "<td>"+$(this).find('buldMnnm').text()+"</td>";
		htmlStr += "<td>"+$(this).find('buldSlno').text()+"</td>";
		htmlStr += "<td>"+$(this).find('mtYn').text()+"</td>";
		htmlStr += "<td>"+$(this).find('lnbrMnnm').text()+"</td>";
		htmlStr += "<td>"+$(this).find('lnbrSlno').text()+"</td>";
		htmlStr += "<td>"+$(this).find('emdNo').text()+"</td>";
		htmlStr += "</tr>";
	});
	htmlStr += "</table>";
	// 결과 HTML을 FORM의 결과 출력 DIV에 삽입
	$("#list").html(htmlStr);
}	

function makeListJson(jsonStr){
	var htmlStr = "";
	htmlStr += "<table>";
	// jquery를 이용한 JSON 결과 데이터 파싱
	$(jsonStr.results.juso).each(function(){
		htmlStr += "<tr>";
		htmlStr += "<td>"+this.roadAddr+"</td>";
		htmlStr += "<td>"+this.roadAddrPart1+"</td>";
		htmlStr += "<td>"+this.roadAddrPart2+"</td>";
		htmlStr += "<td>"+this.jibunAddr+"</td>";
		htmlStr += "<td>"+this.engAddr+"</td>";
		htmlStr += "<td>"+this.zipNo+"</td>";
		htmlStr += "<td>"+this.admCd+"</td>";
		htmlStr += "<td>"+this.rnMgtSn+"</td>";
		htmlStr += "<td>"+this.bdMgtSn+"</td>";
		htmlStr += "<td>"+this.detBdNmList+"</td>";
		/** API 서비스 제공항목 확대 (2017.02) **/
		htmlStr += "<td>"+this.bdNm+"</td>";
		htmlStr += "<td>"+this.bdKdcd+"</td>";
		htmlStr += "<td>"+this.siNm+"</td>";
		htmlStr += "<td>"+this.sggNm+"</td>";
		htmlStr += "<td>"+this.emdNm+"</td>";
		htmlStr += "<td>"+this.liNm+"</td>";
		htmlStr += "<td>"+this.rn+"</td>";
		htmlStr += "<td>"+this.udrtYn+"</td>";
		htmlStr += "<td>"+this.buldMnnm+"</td>";
		htmlStr += "<td>"+this.buldSlno+"</td>";
		htmlStr += "<td>"+this.mtYn+"</td>";
		htmlStr += "<td>"+this.lnbrMnnm+"</td>";
		htmlStr += "<td>"+this.lnbrSlno+"</td>";
		htmlStr += "<td>"+this.emdNo+"</td>";
		htmlStr += "</tr>";
	});
	htmlStr += "</table>";
	// 결과 HTML을 FORM의 결과 출력 DIV에 삽입
	$("#list").html(htmlStr);
}
</script>


</html>
