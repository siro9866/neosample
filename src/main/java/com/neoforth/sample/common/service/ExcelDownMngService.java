package com.neoforth.sample.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.neoforth.sample.common.vo.FileVO;


public interface ExcelDownMngService {
	
	/**
	 * 파일 다운로드 서비스
	 *
	 * @Method Name : fileDownLoad
	 * @작성일 : 2019. 2. 14.
	 * @작성자 : s1212919
	 * @변경이력 : 
	 * @Method 설명 : 
	 * @param fileVO
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	public void fileDownLoad(@ModelAttribute FileVO fileVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 샘플다운로드 싱글탭
	 * @param excelData
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void mysqlExcelDownload(Map<String, Object> excelData, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 샘플다운로드 다중탭
	 * @param excelData
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void mysqlExcelDownloadALL(List<Map<String, Object>> excelData, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
