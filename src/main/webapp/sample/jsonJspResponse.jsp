<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="org.json.simple.parser.ParseException"%>

<%
	
	System.out.println(request.getParameter("json"));
	
    
    try {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(request.getParameter("json"));
        JSONArray persons = (JSONArray) jsonObj.get("persons");
        JSONArray books = (JSONArray) jsonObj.get("books");

        System.out.println("=====Members=====");
        for(int i=0 ; i<persons.size() ; i++){
            JSONObject tempObj = (JSONObject) persons.get(i);
            System.out.println(""+(i+1)+"번째 멤버의 이름 : "+tempObj.get("name"));
            System.out.println(""+(i+1)+"번째 멤버의 nickname : "+tempObj.get("nickname"));
            System.out.println(""+(i+1)+"번째 멤버의 나이 : "+tempObj.get("age"));
            System.out.println("----------------------------");
        }
        for(int i=0 ; i<books.size() ; i++){
            JSONObject tempObj = (JSONObject) books.get(i);
            System.out.println(""+(i+1)+"번째 book 제목 : "+tempObj.get("name"));
            System.out.println(""+(i+1)+"번째 book 저자 : "+tempObj.get("writer"));
            System.out.println(""+(i+1)+"번째 book 가격 : "+tempObj.get("price"));
            System.out.println(""+(i+1)+"번째 book 장르 : "+tempObj.get("genre"));
            System.out.println("----------------------------");
        }

    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

	
%>


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
		<!-- 게시판 영역 -->
		
		<div class="button_wrap">
			
			<button type="button" id="btnForm">등록</button>
		</div>
		
		
	</div>
</body>

<script>


</script>


</html>
