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

</head>

<body>
	<div id="wrap">
		<h3 class="cont_tit">MySQL</h3>
		
		<form id="frm" method="post">
			<input type="hidden" name="pageNum" id="pageNum" value="${paramVO.pageNum}"/>
			<input type="hidden" name="searchValue" value="${paramVO.searchValue}"/>
			<input type="hidden" name="sample_id" value="${result.sample_id }"/>
		</form>	 

		<!-- 게시판 영역 -->
		<table summary="MySQL 상세보기">
			<caption>MySQL 상세보기</caption>
			<colgroup>
				<col width="25%">
				<col width="*">
				<col width="25%">
				<col width="25%">
			</colgroup>
			<tbody>
				<tr>
					<th>이름</th>
					<td colspan="3">${result.sample_name }</td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3">${result.sample_subject }</td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3">${result.sample_contents }</td>
				</tr>
				<tr>
					<th>정렬순서</th>
					<td colspan="3">${result.sample_order }</td>
				</tr>
				<tr>
					<th>중요표시</th>
					<td colspan="3">${result.hot_yn }</td>
				</tr>
				<tr>
					<th>삭제여부</th>
					<td colspan="3">${result.del_yn }</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${result.in_user }</td>
					<th>작성일시</th>
					<td>
						<fmt:parseDate var="in_date" value="${result.in_date }" pattern="yyyy-MM-dd HH:mm:ss"/>
						<fmt:formatDate value="${in_date }" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<th>첨부파일1</th>
					<td>
						<c:choose>
							<c:when test="${empty result.fileList1 }">
							</c:when>
							<c:otherwise>
								<c:forEach items="${result.fileList1 }" var="fileList1">
									<a href="javascript:file_download('${fileList1.file_path }', '${fileList1.org_file_name }', '${fileList1.sys_file_name }')">${fileList1.org_file_name }</a>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</td>
					<th>첨부파일2</th>
					<td>
						<c:choose>
							<c:when test="${empty result.fileList2 }">
							</c:when>
							<c:otherwise>
								<c:forEach items="${result.fileList2 }" var="fileList2">
									<a href="javascript:file_download('${fileList2.file_path }', '${fileList2.org_file_name }', '${fileList2.sys_file_name }')">${fileList2.org_file_name }</a>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- // 게시판 영역 -->
		<div class="button_wrap">
			<button type="button" id="btnList">목록</button>
			<button type="button" id="btnForm">수정</button>
			<button type="button" id="btnDel">삭제</button>
		</div>
	</div>
	
	<form id="file_frm" method="post">
		<input type="hidden" name="file_path" />
		<input type="hidden" name="org_file_name" />
		<input type="hidden" name="sys_file_name" />
	</form>
	
</body>

<script>
var url_list = "/sample/mysql/mysqlList.neo";
var detailUrl = "/sample/mysql/mysqlView.neo";
var url_form = "/sample/mysql/mysqlForm.neo";
var url_delete = "/sample/mysql/mysqlDelete.neo";

$(function(){
	
	//목록 이벤트
	$("#btnList").on("click", function(){
		$("#frm").attr("action", url_list);
		$("#frm").submit();
	});
	//수정 이벤트
	$("#btnForm").on("click", function(){
		$("#frm").attr("action", url_form);
		$("#frm").submit();
	});
	
	//삭제 이벤트
	$("#btnDel").on("click", function(){
		var goUrl = url_delete;
		
 		$.ajax({
			type:"post",
			async:true,
			url:goUrl,
			data:$("#frm").serialize(),
			dataType:"json",
			beforeSend:beforSubmit,
			success:function(responseData){
				var data = responseData.resultJson;
				if(data.rCode == "0000"){
					alert("삭제 되었습니다.");
					$("#frm").attr("action", data.goUrl);
					$("#frm").submit();
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
	
	// 폼 데이터 점검
	beforSubmit = function(){
		if(!confirm("삭제하시겠습니까?")){
			return false;
		}
	};		

});

function file_download(file_path, org_file_name, sys_file_name) {
	$("#file_frm input[name=file_path]").val(file_path);
	$("#file_frm input[name=org_file_name]").val(org_file_name);
	$("#file_frm input[name=sys_file_name]").val(sys_file_name);
	
	$("#file_frm").attr("action", "/sample/common/fileDownLoad.neo");
	$("#file_frm").submit();
	
}
</script>

</html>
