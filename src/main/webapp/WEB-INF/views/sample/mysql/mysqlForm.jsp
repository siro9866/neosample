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
<title>MySQL 쓰기</title>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.js"></script>

</head>

<body>
	<div id="wrap">
		<h3 class="cont_tit">샘플</h3>
		<!-- 게시판 영역 -->
		<form name="frm" id="frm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="sample_id" value="${result.sample_id }">
			<table summary="MySQL 상세보기">
				<caption>MySQL 글쓰기</caption>
				<colgroup>
					<col width="15%">
					<col width="85%">
				</colgroup>
				<tbody>
					<tr>
						<th>이름</th>
						<td colspan="3"><input type="text" class="write_input" name="sample_name" value="${result.sample_name }"></td>
					</tr>
					<tr>
						<th>제목</th>
						<td colspan="3"><input type="text" class="write_input" name="sample_subject" value="${result.sample_subject }"></td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="3"><input type="text" class="write_input" name="sample_contents" value="${result.sample_contents }"></td>
					</tr>
					<tr>
						<th>정렬순서</th>
						<td colspan="3"><input type="text" class="write_input" name="sample_order" value="${result.sample_order }"></td>
					</tr>
					<tr>
						<th>중요표시</th>
						<td colspan="3"><input type="text" class="write_input" name="hot_yn" value="${result.hot_yn }"></td>
					</tr>
					<tr>
						<th>첨부파일1</th>
						<td colspan="3">
							<div>
								<c:choose>
									<c:when test="${empty result.fileList1 }">
										<input type="file" class="upload-hidden" name="upload_group_sub_sample1">
									</c:when>
									<c:otherwise>
										<c:forEach items="${result.fileList1 }" var="fileList1">
											<span id="fileList1_${fileList1.file_id }">${fileList1.org_file_name } <a href="javascript:deleteFile('1', ${fileList1.file_id })">삭제</a></span>
											<input type="file" class="upload-hidden" name="upload_group_sub_sample1">
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
					<tr>
						<th>첨부파일2</th>
						<td colspan="3">
							<div>
								<c:choose>
									<c:when test="${empty result.fileList2 }">
										<input type="file" class="upload-hidden" name="upload_group_sub_sample2">
									</c:when>
									<c:otherwise>
										<c:forEach items="${result.fileList2 }" var="fileList2">
											<span id="fileList2${fileList2.file_id }">${fileList2.org_file_name } <a href="javascript:deleteFile('2', ${fileList2.file_id })">삭제</a></span>
											<input type="file" class="upload-hidden" name="upload_group_sub_sample2">
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- // 게시판 영역 -->
			<div class="button_wrap">
				<button type="button" id="btnSave">쓰기</button>
				<button type="button" id="btnCancel">취소</button>
			</div>
		</form>
	</div>
</body>
<script>
var url_list = "/sample/mysql/mysqlList.neo";
var url_view = "/sample/mysql/mysqlView.neo";
var url_update = "/sample/mysql/mysqlUpdate.neo";
var url_insert = "/sample/mysql/mysqlInsert.neo";

$(function(){
	//취소 이벤트
	$("#btnCancel").on("click", function(){
		var goUrl = "";
		if($("#frm input[name=sample_id]").val().length > 0){
			goUrl = url_view;
		}else{
			goUrl = url_list;
		}
		
		$("#frm").attr("action", goUrl);
		$("#frm").submit();
	});
	
	//등록 이벤트
	$("#btnSave").on("click", function(){
		
		if(!confirm("등록하시겠습니까?")){
			return false;
		}
		
		var goUrl = "";
		if($("#frm input[name=sample_id]").val().length > 0){
			goUrl = url_update;
		}else{
			goUrl = url_insert;
		}
		var btnObj = $(this);
		$("#frm").ajaxSubmit({
			type:"post",
			async:true,
			url:goUrl,
			dataType:"json",
			success:function(responseData){
				var data = responseData.resultJson;
				if(data.rCode == "0000"){
					alert("저장 되었습니다.");
					$("#frm").attr("action", data.goUrl);
					$("#frm input[name=sample_id]").val(responseData.paramVO.sample_id);
					$("#frm").submit();
				}else{
					alert(data.rMsg);
					return;
				}	
			},
			error: function (jqXHR, exception) {
				alert("["+jqXHR.status+"]오류입니다.\n"+exception);
				console.log("AjaxSubmit E:"+exception +" - " + jqXHR.responseText);
				btnObj.prop("disabled", false);
				return;
			},
			beforeSend:function(){btnObj.prop("disabled", true);}
		});
		
		
		// 첨부파일없는 전송의 경우
// 		$.ajax({
// 			type:"post",
// 			async:true,
// 			url:goUrl,
// 			data:$("#frm").serialize(),
// 			dataType:"json",
// 			success:function(responseData){
// 				var data = responseData.resultJson;
// 				if(data.rCode == "0000"){
// 					alert("저장 되었습니다.");
// 					$("#frm").attr("action", data.goUrl);
// 					$("#frm").submit();
// 				}else{
// 					alert(data.rMsg);
// 					return;
// 				}	
// 			},
// 			error: function (jqXHR, exception) {
// 				alert("["+jqXHR.status+"]오류입니다.\n"+exception);
// 				return;
// 			}
// 		});

	});	

});

function deleteFile(idx, file_id) {
	
	var goUrl = "/sample/common/deleteFile.neo";
	
	if(!confirm("삭제 하시겠습니까?\n삭제된 데이타는 복원되지 않습니다.")){
		return false;
	}
	
	$.ajax({
		type:"post",
		async:true,
		url:goUrl,
		data:{"file_id":file_id},
		dataType:"json",
		success:function(responseData){
			var data = responseData.resultJson;
			if(data.rCode == "0000"){
				$("#fileList"+idx + "_" + file_id).css("display", "none");
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
	
}
</script>

</html>