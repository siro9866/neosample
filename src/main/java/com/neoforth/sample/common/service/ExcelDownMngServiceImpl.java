package com.neoforth.sample.common.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.neoforth.sample.common.controller.FileMngController;
import com.neoforth.sample.common.vo.FileVO;
import com.neoforth.sample.utils.CommonUtil;
import com.neoforth.sample.utils.FileUtil;
import com.neoforth.sample.utils.StringUtil;



@Service("sample.common.excelDownMngService")
public class ExcelDownMngServiceImpl implements ExcelDownMngService{

	private static final Logger logger = LoggerFactory.getLogger(ExcelDownMngServiceImpl.class);

	@Value("#{config['FILE_STORE_PATH']}") String FILE_STORE_PATH;
	@Value("#{config['EXCEL_UPLOAD_PATH']}") String EXCEL_UPLOAD_PATH;
	
	/**
	 * 파일다운로드 서비스
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
	public void fileDownLoad(@ModelAttribute FileVO fileVO, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String filePath = fileVO.getFile_path();
		if(filePath != null && !"".equals(filePath)) {
			filePath = filePath.replace(".", " ");
			filePath = filePath.replace("\\", "");
			filePath = filePath.replace("&", " ");
			filePath = filePath.replaceAll(" ", "");
			fileVO.setFile_path(filePath);
		}
	
		
		String sysFilePath = fileVO.getSys_file_name();
		if(sysFilePath != null && !"".equals(sysFilePath)) {
			sysFilePath = sysFilePath.replace("\\.\\.", " ");
			sysFilePath = sysFilePath.replace("\\", "");
			sysFilePath = sysFilePath.replace("&", " ");
			sysFilePath = sysFilePath.replaceAll(" ", "");
			fileVO.setSys_file_name(sysFilePath);
		}
		
		File uFile = new File(FILE_STORE_PATH + fileVO.getFile_path()+File.separator+fileVO.getSys_file_name());
		int fSize = (int) uFile.length();

		logger.debug("FILE DOWNLOAD:"+fSize);
		logger.debug(FILE_STORE_PATH);
		logger.debug(fileVO.getFile_path());
		logger.debug(fileVO.getSys_file_name());
		logger.debug(fileVO.getOrg_file_name());
		
		
		logger.debug("FILE DOWNLOAD:"+fSize);
		logger.debug(FILE_STORE_PATH);
		logger.debug(fileVO.getFile_path());
		logger.debug(fileVO.getSys_file_name());
		logger.debug(fileVO.getOrg_file_name());
		
		if (fSize > 0) {
			String mimetype = "application/x-msdownload";
			response.setContentType(mimetype);
			
			FileMngController fileMngController = new FileMngController();
			
			fileMngController.setDisposition(fileVO.getOrg_file_name(), request, response);
			response.setContentLength(fSize);

			BufferedInputStream in = null;
			BufferedOutputStream out = null;

			try {
				in = new BufferedInputStream(new FileInputStream(uFile));
				out = new BufferedOutputStream(response.getOutputStream());

				FileCopyUtils.copy(in, out);
				out.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				CommonUtil.close(in, out);
				
				// 파일 삭제
				FileUtil.deleteFileInf(fileVO.getSys_file_name(), FILE_STORE_PATH, EXCEL_UPLOAD_PATH);
				
			}

		} else {
			response.setContentType("application/x-msdownload");

			PrintWriter printwriter = response.getWriter();

			printwriter.println("<html>");
			printwriter.println("<br><br><br><h2>Could not get file name:<br>" + fileVO.getOrg_file_name() + "</h2>");
			printwriter.println("</html>");

			printwriter.flush();
			printwriter.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void mysqlExcelDownload(Map<String, Object> excelData, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fileExt = ".xlsx";
		// 실제파일 생성해서 저장
		String orginFileName = (String) excelData.get("excelName") + fileExt;
		String systemFileName = (String) excelData.get("systemFileName") + fileExt;
		String savePath = FILE_STORE_PATH + EXCEL_UPLOAD_PATH;
		File saveFolder = new File(savePath);
		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}
		
		String filePath = savePath + File.separator + systemFileName;
		logger.debug("===================================:"+filePath);
		OutputStream os = new FileOutputStream(filePath);
		// streaming workbook 생성 // 100 row마다 파일로 flush
		@SuppressWarnings("resource")
		Workbook wb = new SXSSFWorkbook(100);
		
		// S: 스타일
		// 헤더 스타일
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 테두리 설정
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		Font font = wb.createFont(); // 폰트 조정 인스턴스 생성
		font.setBoldweight((short) 700);
		headerStyle.setFont(font);
		
		// 바디 스타일
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// E: 스타일
		
		// 헤더
		List<String> colName = new ArrayList<String>();
		colName.add("No");
		colName.add("ID");
		colName.add("이름");
		colName.add("제목");
		colName.add("내용");
		colName.add("정렬순서");
		colName.add("중요표시");
		colName.add("작성일");
		colName.add("작성자");

		List<String> colName2 = new ArrayList<String>();
		colName2.add("No");
		colName2.add("ID");
		colName2.add("이름");
		colName2.add("제목");
		colName2.add("내용");
		colName2.add("정렬순서");
		colName2.add("중요표시");
		colName2.add("작성일");
		colName2.add("작성자");
		
		// 시트 생성
		String sheetName = (String) excelData.get("excelName");
		
		BigDecimal bdnum = new BigDecimal("0");
		
		Sheet sheet = wb.createSheet(sheetName);

		// 타이틀부분 구현
		Row heading = sheet.createRow(0);
		Row heading2 = sheet.createRow(1);
		for(int i = 0; i < colName.size(); i++) {
			Cell cell = heading.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(colName.get(i));
		}
		for(int i = 0; i < colName2.size(); i++) {
			Cell cell = heading2.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(colName2.get(i));
		}
		
		// 상단메뉴 cell 병합
		//firstRow, lastRow, firstCol, lastCol  0,0 부터 시작
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
		sheet.addMergedRegion(new CellRangeAddress(0,0,1,2));
		sheet.addMergedRegion(new CellRangeAddress(0,0,3,4));
		sheet.addMergedRegion(new CellRangeAddress(0,1,5,5));
		sheet.addMergedRegion(new CellRangeAddress(0,1,6,6));
		sheet.addMergedRegion(new CellRangeAddress(0,1,7,7));
		sheet.addMergedRegion(new CellRangeAddress(0,1,8,8));
		
		List<Map<String, Object>> colValue = (List<Map<String, Object>>) excelData.get("colValue");
		logger.debug("colValue.size:"+colValue.size());
		Map<String, Object> item = new HashMap<String, Object>();
		
		// 리스트 구성
		for(int i=0; i<colValue.size(); i++){
			item = colValue.get(i);
			Row row = sheet.createRow(2 + i);
			int nCnt = 0;
			for(int j=0; j<colName.size(); j++){
				sheet.setColumnWidth(j, 256 * 20);
			}
			// 번호
			row.createCell(nCnt++).setCellValue(i+1);
			// 문자
			//row.createCell(nCnt++).setCellValue(String.valueOf(item.get("SAMPLE_ID")));
			// 숫자
			row.createCell(nCnt++).setCellValue((StringUtil.nvl(new BigDecimal(String.valueOf(item.get("SAMPLE_ID"))), bdnum)).doubleValue());
			row.createCell(nCnt++).setCellValue((String) item.get("SAMPLE_NAME"));
			row.createCell(nCnt++).setCellValue((String) item.get("SAMPLE_SUBJECT"));
			row.createCell(nCnt++).setCellValue((String) item.get("SAMPLE_CONTENTS"));
			row.createCell(nCnt++).setCellValue(String.valueOf(item.get("SAMPLE_ORDER")));
			row.createCell(nCnt++).setCellValue((String) item.get("HOT_YN"));
			row.createCell(nCnt++).setCellValue(StringUtil.dateToString(item.get("IN_DATE"), "yyyy-MM-dd"));
			row.createCell(nCnt++).setCellValue((String) item.get("IN_USER"));
		}
		
		logger.info("Excel 포 끝");
		
		// 반드시 종료
		wb.write(os);
		os.close();
		((SXSSFWorkbook)wb).dispose();
		
		// 생성한 파일을 다운로드 함
		FileVO fileVO = new FileVO();
		fileVO.setFile_path(EXCEL_UPLOAD_PATH);
		fileVO.setOrg_file_name(orginFileName);
		fileVO.setSys_file_name(systemFileName);
		
		// 파일 다운
		fileDownLoad(fileVO, request, response);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void mysqlExcelDownloadALL(List<Map<String, Object>> excelDatas, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fileExt = ".xlsx";
		// 실제파일 생성해서 저장
		String orginFileName = (String) excelDatas.get(0).get("excelName") + fileExt;
		String systemFileName = (String) excelDatas.get(0).get("systemFileName") + fileExt;
		String savePath = FILE_STORE_PATH + EXCEL_UPLOAD_PATH;
		File saveFolder = new File(savePath);
		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}
		
		String filePath = savePath + File.separator + systemFileName;
		logger.debug("===================================:"+filePath);
		OutputStream os = new FileOutputStream(filePath);
		// streaming workbook 생성 // 100 row마다 파일로 flush
		@SuppressWarnings("resource")
		Workbook wb = new SXSSFWorkbook(100);
		
		// S: 스타일
		// 헤더 스타일
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 테두리 설정
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		Font font = wb.createFont(); // 폰트 조정 인스턴스 생성
		font.setBoldweight((short) 700);
		headerStyle.setFont(font);
		
		// 바디 스타일
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// E: 스타일
		
		List<String> colName = new ArrayList<String>();
		colName.add("No");
		colName.add("ID");
		colName.add("이름");
		colName.add("제목");
		colName.add("내용");
		colName.add("정렬순서");
		colName.add("중요표시");
		colName.add("작성일");
		colName.add("작성자");

		List<String> colName2 = new ArrayList<String>();
		colName2.add("No");
		colName2.add("ID");
		colName2.add("이름");
		colName2.add("제목");
		colName2.add("내용");
		colName2.add("정렬순서");
		colName2.add("중요표시");
		colName2.add("작성일");
		colName2.add("작성자");
		
		// 시트 생성
		BigDecimal bdnum = new BigDecimal("0");
		for(Map<String, Object> excelData : excelDatas){
			
			logger.info((String) excelData.get("sheetName"));
			
			Sheet sheet = wb.createSheet((String) excelData.get("sheetName"));
			
			// 타이틀부분 구현
			Row heading = sheet.createRow(0);
			Row heading2 = sheet.createRow(1);
			for(int i = 0; i < colName.size(); i++) {
				Cell cell = heading.createCell(i);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(colName.get(i));
			}
			for(int i = 0; i < colName2.size(); i++) {
				Cell cell = heading2.createCell(i);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(colName2.get(i));
			}
			
			// 상단메뉴 cell 병합
			//firstRow, lastRow, firstCol, lastCol  0,0 부터 시작
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
			sheet.addMergedRegion(new CellRangeAddress(0,0,1,2));
			sheet.addMergedRegion(new CellRangeAddress(0,0,3,4));
			sheet.addMergedRegion(new CellRangeAddress(0,1,5,5));
			sheet.addMergedRegion(new CellRangeAddress(0,1,6,6));
			sheet.addMergedRegion(new CellRangeAddress(0,1,7,7));
			sheet.addMergedRegion(new CellRangeAddress(0,1,8,8));
			
			List<Map<String, Object>> colValue = (List<Map<String, Object>>) excelData.get("colValue");
			Map<String, Object> item = new HashMap<String, Object>();
			
			// 리스트 구성
			for(int i=0; i<colValue.size(); i++){
				item = colValue.get(i);
				Row row = sheet.createRow(2 + i);
				int nCnt = 0;
				for(int j=0; j<colName.size(); j++){
					sheet.setColumnWidth(j, 256 * 20);
				}
				// 번호
				row.createCell(nCnt++).setCellValue(i+1);
				// 문자
				row.createCell(nCnt++).setCellValue(String.valueOf(item.get("SAMPLE_ID")));
				// 숫자
//				row.createCell(nCnt++).setCellValue((StringUtil.nvl(new BigDecimal(String.valueOf(item.get("SAMPLE_ID"))), bdnum)).doubleValue());
				row.createCell(nCnt++).setCellValue((String) item.get("SAMPLE_NAME"));
				row.createCell(nCnt++).setCellValue((String) item.get("SAMPLE_SUBJECT"));
				row.createCell(nCnt++).setCellValue((String) item.get("SAMPLE_CONTENTS"));
				row.createCell(nCnt++).setCellValue(String.valueOf(item.get("SAMPLE_ORDER")));
				row.createCell(nCnt++).setCellValue((String) item.get("HOT_YN"));
				row.createCell(nCnt++).setCellValue(StringUtil.dateToString(item.get("IN_DATE"), "yyyy_MM_dd"));
				row.createCell(nCnt++).setCellValue((String) item.get("IN_USER"));
			}
		}
		
		
		logger.info("Excel 포 끝");
		
		// 반드시 종료
		wb.write(os);
		os.close();
		((SXSSFWorkbook)wb).dispose();
		
		// 생성한 파일을 다운로드 함
		FileVO fileVO = new FileVO();
		fileVO.setFile_path(EXCEL_UPLOAD_PATH);
		fileVO.setOrg_file_name(orginFileName);
		fileVO.setSys_file_name(systemFileName);
		
		// 파일 다운
		fileDownLoad(fileVO, request, response);
		
	}

}
