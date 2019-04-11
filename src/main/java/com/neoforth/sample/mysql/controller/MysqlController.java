package com.neoforth.sample.mysql.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.neoforth.sample.common.service.ExcelDownMngService;
import com.neoforth.sample.common.view.PagingView;
import com.neoforth.sample.mysql.service.MysqlService;
import com.neoforth.sample.mysql.vo.MysqlVO;
import com.neoforth.sample.utils.CommonUtil;

import net.sf.json.JSONObject;


@Controller("sample.mysqlController")
@RequestMapping(value="/sample/mysql")
public class MysqlController{

	private static final Logger logger = LoggerFactory.getLogger(MysqlController.class);
	
	@Resource(name="sample.mysqlService")
	private MysqlService mysqlService;
	
	@Resource(name="sample.common.excelDownMngService")
	private ExcelDownMngService excelDownMngService;
	
	@Value("#{config['UPLOAD_GROUP_SAMPLE']}") String FILE_UPLOAD_GROUP;	
	
	/**
	 * 리스트
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/mysqlList")
	public ModelAndView mysqlList(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		List<MysqlVO> resultList = new ArrayList<MysqlVO>();

//		paramVO.setBlockSize(2);
//		paramVO.setPageSize(5); 
		CommonUtil.setPageRow(paramVO);

		paramVO.setUpload_group(FILE_UPLOAD_GROUP);
		
		int totCnt = 0;
		try {
			totCnt = mysqlService.mysqlListCnt(paramVO);
			if(totCnt > 0){
				resultList = mysqlService.mysqlList(paramVO);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(totCnt > 0){
			//paging		
			PagingView pv = new PagingView(paramVO.getPageNum(), paramVO.getPageSize(), paramVO.getBlockSize(), totCnt);
			mav.addObject("paging", pv.print());
			//paging		
		}
		
		logger.debug("paramVO:"+paramVO.toStringVo());
		
		mav.addObject("paramVO", paramVO);
		mav.addObject("totCnt", totCnt);
		mav.addObject("resultList", resultList);
		
		mav.setViewName("sample/mysql/mysqlList");
		
		return mav;
	}
	
	/**
	 * 상세
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/mysqlView")
	public ModelAndView mysqlView(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/mysql/mysqlView");
		MysqlVO result = new MysqlVO();
		
		try {
			result = mysqlService.mysqlView(paramVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mav.addObject("paramVO", paramVO);
		mav.addObject("result", result);
		
		return mav;
	}
	
	/**
	 * 등록 수정폼
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/mysqlForm")
	public ModelAndView mysqlForm(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("sample/mysql/mysqlForm");
		MysqlVO result = new MysqlVO();
		try {
			result = mysqlService.mysqlView(paramVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mav.addObject("paramVO", paramVO);
		mav.addObject("result", result);
		return mav;
	}	
	
	/**
	 * 등록
	 * @param paramVO
	 * @param multiRequest
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/mysqlInsert")
	public ModelAndView mysqlInsert(@ModelAttribute MysqlVO paramVO, MultipartHttpServletRequest multiRequest, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		JSONObject json = new JSONObject();
		mav.setViewName("jsonView");
		CommonUtil.getReturnCodeFail(json);
		
		try {
			CommonUtil.setInUserInfo(multiRequest, paramVO);	
			mysqlService.mysqlInsert(multiRequest, paramVO);
			CommonUtil.getReturnCodeSuc(json);
			json.put("goUrl", "/sample/mysql/mysqlList.neo");
		} catch (Exception e) {
			logger.debug("EXCEPTION insert E:" + e.toString());
			CommonUtil.getReturnCodeFail(json);
			e.printStackTrace();
		}
		logger.debug(json.toString());
		mav.addObject("paramVO", paramVO);
		mav.addObject("resultJson", json);
		return mav;
		
	}
	
	/**
	 * 수정
	 * @param paramVO
	 * @param multiRequest
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/mysqlUpdate")
	public ModelAndView mysqlUpdate(@ModelAttribute MysqlVO paramVO, MultipartHttpServletRequest multiRequest, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		JSONObject json = new JSONObject();
		mav.setViewName("jsonView");
		CommonUtil.getReturnCodeFail(json);
		
		try {
			CommonUtil.setUpUserInfo(multiRequest, paramVO);
			json = mysqlService.mysqlUpdate(multiRequest, paramVO);
			
			CommonUtil.getReturnCodeSuc(json);
			json.put("goUrl", "/sample/mysql/mysqlView.neo");
		} catch (Exception e) {
			logger.debug("EXCEPTION update E:" + e.toString());
			CommonUtil.getReturnCodeFail(json);
			e.printStackTrace();
		}
		logger.debug(json.toString());
		mav.addObject("paramVO", paramVO);
		mav.addObject("resultJson", json);
		return mav;
	}
	
	/**
	 * 삭제
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/mysqlDelete")
	public ModelAndView mysqlDelete(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		JSONObject json = new JSONObject();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		try {
			CommonUtil.setUpUserInfo(request, paramVO);			
			mysqlService.mysqlDelete(paramVO);
			CommonUtil.getReturnCodeSuc(json);
			json.put("goUrl", "/sample/mysql/mysqlList.neo");
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("PROGRAM_Exception:"+e);
			CommonUtil.getReturnCodeFail(json);
			e.printStackTrace();
		}
		logger.debug(json.toString());
		mav.addObject("paramVO", paramVO);
		mav.addObject("resultJson", json);
		return mav;
	}
	
	
	@RequestMapping(value="/mysqlExcelDownload")
	public void mysqlExcelDownload(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ParseException {
		
		SimpleDateFormat formtter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String str = formtter.format(date);
		String excelFileName = "";
		// 엑셀생성자
		Map<String, Object> excelData = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		try {
			
			logger.info("엑셀 다운로드 싱글탭:"+paramVO.toStringVo());

			resultList = mysqlService.excelDownload(paramVO);
			
			// 파일명이나 탭명에 / 있으면 생성중 오류발생해서 꼭 치환해줘야함
			// excelFileName.replaceAll("/", "");
			excelFileName = "MYSQL";
			logger.debug("excelFileName:"+ excelFileName+"_"+str);
			excelData.put("excelName", excelFileName+"_"+str);
			excelData.put("systemFileName", "excelDownload_"+CommonUtil.getNowTime());
			excelData.put("colValue", resultList);
			
			// excel 생성
			excelDownMngService.mysqlExcelDownload(excelData, request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("EXCEPTION:" + e.toString());
		}

		
	}
	
	@RequestMapping(value="/mysqlExcelDownloadALL")
	public void mysqlExcelDownloadALL(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ParseException {
		
		logger.info("엑셀 다운로드 다중탭:"+paramVO.toStringVo());
		
		SimpleDateFormat formtter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String str = formtter.format(date);
		String excelFileName = "";
		// 엑셀생성자
		Map<String, Object> excelData = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		String sheetName = "";
		int sheetNum = 0;
		// 다중시트 엑셀다운로드
		List<Map<String, Object>> excelDatas = new ArrayList<Map<String, Object>>();
		
		try {
			
			logger.info("엑셀 다운로드 싱글탭:"+paramVO.toStringVo());
			resultList = mysqlService.excelDownload(paramVO);
			
			for(int i=0; i< 5; i++){
				excelData = new HashMap<>();
				sheetNum++;
				
				excelFileName = "MYSQL";
				sheetName = excelFileName + "_" + String.valueOf(sheetNum);
				excelData.put("excelName", excelFileName+"_"+str);
				excelData.put("sheetName", sheetName.replaceAll("/", ""));
				excelData.put("systemFileName", "excelDownload_"+CommonUtil.getNowTime());
				excelData.put("colValue", resultList);
				excelDatas.add(excelData);
			}	
			// excel 생성
			excelDownMngService.mysqlExcelDownloadALL(excelDatas, request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("EXCEPTION:" + e.toString());
		}
	}
	
	
	@RequestMapping(value="/mysqlExcelUpload")
	public ModelAndView mysqlExcelUpload(@ModelAttribute MysqlVO paramVO, MultipartHttpServletRequest multiRequest, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		JSONObject json = new JSONObject();
		JSONObject returnjson = new JSONObject();
		mav.setViewName("jsonView");
		CommonUtil.getReturnCodeFail(json);

		try {
			logger.info("엑셀업로드");
			CommonUtil.setInUserInfo(multiRequest, paramVO);	
			
			returnjson = mysqlService.mysqlExcelUpload(multiRequest, paramVO);
			
			if(returnjson.get("rCode").equals("0000")){
				CommonUtil.getReturnCodeSuc(json);
			}else{
				CommonUtil.getReturnCodeFail(json);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("EXCEPTION insert E:" + e.toString());
			CommonUtil.getReturnCodeFail(json);
		}
		
		json.put("returnjson", returnjson);
		mav.addObject("resultJson", json);
		mav.addObject("paramVO", paramVO);
		
		logger.info("mysqlExcelUpload:" + json.toString());
		
		return mav;
	}
	
}