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
<title>Map 샘플</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

</head>

<body>
	<div id="wrap">
		<h3 class="cont_tit">Map</h3>
		
		<!--	S:검색조건 -->
		<form id="frm" name="frm" method="post">
			<div class="row">
				<div class="col-lg-12 ">
					<div class="col-lg-6 col-md-6">
						<input type="text" class="form-control input-lg" id="searchValue" name="searchValue" value="${paramVO.searchValue }">
						<button type="button" id="btnSearch" class="btn btn-primary btn-lg btn-block"><i class="fa fa-search"></i>&nbsp;검색</button>
					</div>								
				</div>
			</div>
			<!-- /. ROW  -->
		</form>	 
		<!--	S:검색조건 -->
		
		
<!-- 		<div class="button_wrap"> -->
<!-- 			<button type="button" name="btnForm">등록</button> -->
<!-- 			<button type="button" name="btnExcelDownload">EXCEL 다운로드</button> -->
<!-- 			<button type="button" name="btnExcelDownloadALL">EXCEL 다운로드ALL</button> -->
<!-- 		</div> -->
	</div>
	
</body>

<script>

$(function(){
	
	
	
});
</script>


</html>
