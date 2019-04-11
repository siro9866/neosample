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
<title>jsonJsp 샘플</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

</head>

<body>
	<div id="wrap">

		<h3 class="cont_tit">jsonJsp</h3>
		<h3 class="cont_tit">JSP 에서 JSON 으로 던지고 JSP 에서 요청 처리함</h3>
		<!-- 게시판 영역 -->

		<div class="button_wrap">
			<button type="button" id="btnForm">등록</button>
		</div>


	</div>
</body>

<script>

var url_form = "/sample/jsonJspResponse.jsp";

$(function(){
	
	$("#btnForm").on("click", function(){
		
		var goUrl = url_form;

// 		var obj = {};
// 		obj["charlie0"] = "0";
// 		obj["charlie1"] = "1";
// 		obj["charlie2"] = "2";
// 		obj["charlie3"] = "3";
// 		obj["charlie4"] = "4";
	
        //사람 정보
        var personArray = new Array();
           
        var personInfo = new Object();
         
        personInfo.name = "송강호";
        personInfo.age = "25";
        personInfo.gender = "남자";
        personInfo.nickname = "남궁민수";
           
        personArray.push(personInfo);
         
         
        personInfo = new Object();
         
        personInfo.name = "전지현";
        personInfo.age = "21";
        personInfo.gender = "여자";
        personInfo.nickname = "예니콜";
         
        personArray.push(personInfo);
         
        //책 정보
        var bookArray = new Array();
         
        bookInfo = new Object();
         
        bookInfo.name = "사람은 무엇으로 사는가?";
        bookInfo.writer = "톨스토이";
        bookInfo.price = "100";
        bookInfo.genre = "소설";
        bookInfo.publisher = "톨스토이 출판사";
         
        bookArray.push(bookInfo);
         
         
        bookInfo = new Object();
         
        bookInfo.name = "홍길동전";
        bookInfo.writer = "허균";
        bookInfo.price = "300";
        bookInfo.genre = "소설";
        bookInfo.publisher = "허균 출판사";
         
        bookArray.push(bookInfo);
         
         
        bookInfo = new Object();
         
        bookInfo.name = "레미제라블";
        bookInfo.writer = "빅토르 위고";
        bookInfo.price = "900";
        bookInfo.genre = "소설";
        bookInfo.publisher = "빅토르 위고 출판사";
         
        bookArray.push(bookInfo);
         
        //사람, 책 정보를 넣음
        var totalInfo = new Object();
         
        totalInfo.persons = personArray;
        totalInfo.books = bookArray;
        
        var jsonInfo = JSON.stringify(totalInfo);
//         var jsonInfo = JSON.stringify(personArray);
        console.log(jsonInfo); //브라우저 f12개발자 모드에서 confole로 확인 가능
//         alert(jsonInfo);
   
	
	
		
		
		$.ajax({
			type:"post",
			url:goUrl,
			data : {"json" : jsonInfo},
			dataType:"html",
			success:function(responseData){
			},
			error: function (jqXHR, exception) {
				alert("["+jqXHR.status+"]오류입니다.\n"+exception);
				var msg = '';
				if (jqXHR.status === 0) {
					msg = 'Not connect.\n Verify Network.';
				} else if (jqXHR.status == 404) {
					msg = 'Requested page not found. [404]';
				} else if (jqXHR.status == 500) {
					msg = 'Internal Server Error [500].';
				} else if (exception === 'parsererror') {
					msg = 'Requested JSON parse failed.';
				} else if (exception === 'timeout') {
					msg = 'Time out error.';
				} else if (exception === 'abort') {
					msg = 'Ajax request aborted.';
				} else {
					msg = 'Uncaught Error.\n' + jqXHR.responseText;
				}
				console.log(msg)
				return;
			}
		});

	});	

});

</script>


</html>
