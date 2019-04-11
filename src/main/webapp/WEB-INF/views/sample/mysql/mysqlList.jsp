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
<title>Mysql 샘플</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.js"></script>

</head>

<body>
	<div id="wrap">
		<!--	S:검색조건 -->
		<form id="frm" name="frm" method="post">
			<input type="hidden" name="pageNum" id="pageNum" value="${paramVO.pageNum}"/>
			<input type="hidden" name="sample_id" id="sample_id"/>					
			<input type="text" id="dummy" style="display:none"/>					
		
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
		
		<h3 class="cont_tit">MySQL</h3>
		<!-- 게시판 영역 -->
		<table id="dataTable" summary="MySQL 전체 리스트">
			<caption>MySQL 전체 리스트</caption>
			<colgroup>
				<col width="5%">
				<col width="15%">
				<col width="*">
				<col width="5%">
				<col width="5%">
				<col width="15%">
				<col width="10%">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>이름</th>
					<th>제목</th>
					<th>정렬순서</th>
					<th>중요표시</th>
					<th>작성일</th>
					<th>작성자</th>
					<th>자료</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${empty resultList }">
					<tr><td colspan="6">등록된 내용이 없습니다.</td></tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${resultList }" var="result">
						<tr id="${result.sample_id }">
							<td>${result.sample_id }</td>
							<td>${result.sample_name }</td>
							<td>${result.sample_subject }</td>
							<td>${result.sample_order }</td>
							<td>${result.hot_yn }</td>
							<td>
								<fmt:parseDate var="in_date" value="${result.in_date }" pattern="yyyy-MM-dd HH:mm:ss"/>
								<fmt:formatDate value="${in_date }" pattern="yyyy-MM-dd"/>														
							</td>
							<td>${result.in_user }</td>
							<td>
								<c:if test="${!empty result.file_id }">파일</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		<!-- 페이징 영역 -->
		<%@ include file="/WEB-INF/views/sample/common/paging.jsp" %>
		<!-- // 페이징 영역 -->
		
		<div class="button_wrap">
			<button type="button" name="btnForm">등록</button>
			<button type="button" name="btnExcelDownload">EXCEL 다운로드</button>
			<button type="button" name="btnExcelDownloadALL">EXCEL 다운로드ALL</button>
		</div>
	</div>
	
	<br><br><br><br>
	업로드는 위 내용을 다운로드받은 샘플로 작성해서 해주세요
	<form id="frm2" method="post" enctype="multipart/form-data">
		<input type="file" class="upload-hidden" id="excelUploadFile" name="excelUploadFile">
		<br><br>
		<button type="button" name="btnExcelUpload">EXCEL 업로드</button>
	</form>
	
	<form name="excelFrm" method="post">
	</form>
	
</body>

<script>

var url_list = "/sample/mysql/mysqlList.neo";
var url_view = "/sample/mysql/mysqlView.neo";
var url_form = "/sample/mysql/mysqlForm.neo";
var url_ExcelDown = "/sample/mysql/mysqlExcelDownload.neo";
var url_ExcelDownALL = "/sample/mysql/mysqlExcelDownloadALL.neo";
var url_ExcelUpload = "/sample/mysql/mysqlExcelUpload.neo";

$(function(){
	
	//검색어 키업 이벤트발생시 검색버튼 활성화 및 엔터키 이벤트 발생
	$("#searchValue").on("keyup", function(e){
		//엔터키 클릭시 검색
		if (e.keyCode == 13){
			$("#pageNum").val("1");
			$("#frm").attr("action", url_list);
			$("#frm").submit();
		}
	});
	$("#btnSearch").on("click", function(){
		$("#pageNum").val("1");
		$("#frm").attr("action", url_list);
		$("#frm").submit();
	});
	
	//상세 이벤트
	$("#dataTable > tbody > tr").on("click", function() {
		$("input[name=sample_id]").val($(this).attr("id"));
		$("#frm").attr("action", url_view);
		$("#frm").submit();
	});
	
	//등록 이벤트
	$("button[name=btnForm]").on("click", function(){
		$("#frm").attr("action", url_form);
		$("#frm").submit();
	});
	
	//Excel download
	var excelDownFlg = "N";
	$("button[name=btnExcelDownload]").on("click", function(){
		if(excelDownFlg == "Y"){
			alert("다운로드 중입니다. 잠시후 시도 부탁드립니다.");
			return;
		}
		excelDownFlg = "Y";
		if(!confirm('데이타가 많으면 오래걸려요.\n다운받으시겠습니까?')){	
			return;
		}
		$("form[name=excelFrm]").attr("action", url_ExcelDown);
		$("form[name=excelFrm]").submit();
		setTimeout(function(){
			excelDownFlg = "N";
		}, 3000);
	});
	
	$("button[name=btnExcelDownloadALL]").on("click", function(){
		if(excelDownFlg == "Y"){
			alert("다운로드 중입니다. 잠시후 시도 부탁드립니다.");
			return;
		}
		excelDownFlg = "Y";
		if(!confirm('데이타가 많으면 오래걸려요.\n다운받으시겠습니까?')){	
			return;
		}
		$("form[name=excelFrm]").attr("action", url_ExcelDownALL);
		$("form[name=excelFrm]").submit();
		setTimeout(function(){
			excelDownFlg = "N";
		}, 3000);
	});
	
	$("button[name=btnExcelUpload]").on("click", function(){
		var goUrl = url_ExcelUpload;
		
		if ($.trim($("#excelUploadFile").val()) == "") {
			alert("파일을 업로드 해주세요");
			return;
		}
		
		var btnObj = $("#btnExcelUpload");
		// S: 데이타가공
		$("#frm2").ajaxSubmit({
			type:"post",
			async:true,
			url:goUrl,
			dataType:"json",
			timeout:800000,
			success:function(responseData){
				var data = responseData.resultJson;
				if(data.rCode == "0000"){
					alert("업로드에 성공하였습니다.");
				}else{
					alert("업로드에 실패하였습니다.");
					return;
				}	
			},
			error: function (jqXHR, exception) {
	 			alert("업로드에 실패하였습니다.\n"+"["+jqXHR.status+"]오류입니다.\n"+exception);
				console.log(msg);
				return;
			}
			, complete:function(){btnObj.prop("disabled", false);}
		});
	});
});

function search(){
	$("#pageNum").val("1");
	$("#frm").attr("action", url_list);
	$("#frm").submit();
}	

// 페이징
function goPage(pageNo){
	$("#pageNum").val(pageNo);
	$("#searchValue").val("${paramVO.searchValue}");
	$("#frm").attr("action", url_list);
	$("#frm").submit();
};
</script>


</html>
