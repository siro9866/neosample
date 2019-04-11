package com.neoforth.sample.mysql.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neoforth.sample.common.service.FileService;
import com.neoforth.sample.common.vo.FileVO;
import com.neoforth.sample.mysql.dao.MysqlDaoImpl;
import com.neoforth.sample.mysql.vo.MysqlVO;
import com.neoforth.sample.utils.CommonUtil;
import com.neoforth.sample.utils.FileUtil;
import com.neoforth.sample.utils.StringUtil;
import com.neoforth.sample.utils.excel.ExcelCsvFileUtil;
import com.neoforth.sample.utils.excel.ExcelReadOption;

import net.sf.json.JSONObject;

@Service("sample.mysqlService")
public class MysqlServiceImpl implements MysqlService{

	private static final Logger logger = LoggerFactory.getLogger(MysqlServiceImpl.class);
	
	@Resource(name="sample.mysqlDAO")
	private MysqlDaoImpl mysqlDAO;
	@Resource(name="sample.common.fileService")
	private FileService fileService;
	
	@Value("#{config['FILE_STORE_PATH']}") String FILE_STORE_PATH;
	@Value("#{config['SAMPLE_UPLOAD_PATH']}") String UPLOAD_PATH;
	@Value("#{config['SAMPLE_FILE_PREFIX']}") String FILE_PREFIX;

	@Value("#{config['MASTER_TABLE_SAMPLE']}") String MASTER_TABLE_SAMPLE;
	@Value("#{config['UPLOAD_GROUP_SAMPLE']}") String FILE_UPLOAD_GROUP;
	@Value("#{config['UPLOAD_GROUP_SUB_SAMPLE1']}") String UPLOAD_GROUP_SUB_SAMPLE1;
	@Value("#{config['UPLOAD_GROUP_SUB_SAMPLE2']}") String UPLOAD_GROUP_SUB_SAMPLE2;

	@Value("#{config['EXCEL_UPLOAD_PATH']}") String EXCEL_UPLOAD_PATH;
	@Value("#{config['UPLOAD_GROUP_SAMPLE_EXCEL']}") String UPLOAD_GROUP_SAMPLE_EXCEL;
	@Value("#{config['UPLOAD_GROUP_SUB_SAMPLE_EXCEL']}") String UPLOAD_GROUP_SUB_SAMPLE_EXCEL;
	
	
	@Override
	public int mysqlListCnt(MysqlVO paramVO) throws Exception {
		return mysqlDAO.mysqlListCnt(paramVO);
	}

	@Override
	public List<MysqlVO> mysqlList(MysqlVO paramVO) throws Exception{
		return mysqlDAO.mysqlList(paramVO);
	}
	
	@Override
	public MysqlVO mysqlView(MysqlVO paramVO) throws Exception{
		
		
		MysqlVO resultVO = new MysqlVO();
		resultVO = mysqlDAO.mysqlView(paramVO);
		
		// 첨부파일 조회
		FileVO fileVO = new FileVO();
		fileVO.setMaster_id(paramVO.getSample_id());
		fileVO.setUpload_group(FILE_UPLOAD_GROUP);
		
		fileVO.setUpload_group_sub(UPLOAD_GROUP_SUB_SAMPLE1);
		List<FileVO> fileList1 = new ArrayList<FileVO>(); 
		fileList1 = fileService.getFileList(fileVO);
		resultVO.setFileList1(fileList1);

		fileVO.setUpload_group_sub(UPLOAD_GROUP_SUB_SAMPLE2);
		List<FileVO> fileList2 = new ArrayList<FileVO>(); 
		fileList2 = fileService.getFileList(fileVO);
		resultVO.setFileList2(fileList2);
		// 첨부파일 조회
		
		return resultVO ;
	}

	@Override
	public JSONObject mysqlInsert(MultipartHttpServletRequest multiRequest, MysqlVO paramVO) throws Exception{
		JSONObject json = new JSONObject();
		mysqlDAO.mysqlInsert(paramVO);
		
		//파일정보
		Map<String, MultipartFile> mFiles = multiRequest.getFileMap();
		if (!mFiles.isEmpty()) {
			List<FileVO> result = FileUtil.parseFileInf(mFiles, FILE_PREFIX, FILE_STORE_PATH, UPLOAD_PATH);
			if(result != null && !result.isEmpty()){
				for(int i=0; i<result.size(); i++){
					FileVO fileVO = result.get(i);
					CommonUtil.setInUserInfo(multiRequest, fileVO);
					String upload_gr_sub = "";
					if("upload_group_sub_sample1".equalsIgnoreCase(fileVO.getElement_nm())){
						// 샘플1
						upload_gr_sub = UPLOAD_GROUP_SUB_SAMPLE1;
					}else if("upload_group_sub_sample2".equalsIgnoreCase(fileVO.getElement_nm())){
						// 샘플2
						upload_gr_sub = UPLOAD_GROUP_SUB_SAMPLE2;
					}
					fileVO.setUpload_group(FILE_UPLOAD_GROUP);
					fileVO.setUpload_group_sub(upload_gr_sub);
					fileVO.setMaster_id(paramVO.getSample_id());
					fileVO.setMaster_table(MASTER_TABLE_SAMPLE);
					fileService.insertFile(fileVO);
				}
			}
		}
		
		return json;		
	}

	@Override
	public JSONObject mysqlUpdate(MultipartHttpServletRequest multiRequest, MysqlVO paramVO) throws Exception{
		
		JSONObject json = new JSONObject();
		
		mysqlDAO.mysqlUpdate(paramVO);
		
		//파일정보
		Map<String, MultipartFile> mFiles = multiRequest.getFileMap();
		if (!mFiles.isEmpty()) {
			List<FileVO> result = FileUtil.parseFileInf(mFiles, FILE_PREFIX, FILE_STORE_PATH, UPLOAD_PATH);
			if(result != null && !result.isEmpty()){
				for(int i=0; i<result.size(); i++){
					FileVO fileVO = result.get(i);
					String upload_gr_sub = "";
					if("upload_group_sub_sample1".equalsIgnoreCase(fileVO.getElement_nm())){
						// 샘플1
						upload_gr_sub = UPLOAD_GROUP_SUB_SAMPLE1;
					}else if("upload_group_sub_sample2".equalsIgnoreCase(fileVO.getElement_nm())){
						// 샘플2
						upload_gr_sub = UPLOAD_GROUP_SUB_SAMPLE2;
					}
					fileVO.setUpload_group(FILE_UPLOAD_GROUP);
					fileVO.setUpload_group_sub(upload_gr_sub);
					fileVO.setMaster_id(paramVO.getSample_id());
					fileVO.setMaster_table(MASTER_TABLE_SAMPLE);
					fileService.insertFile(fileVO);
					// 기존파일 삭제
					CommonUtil.setUpUserInfo(multiRequest, fileVO);
					fileService.deleteFileList(fileVO);
					// 신규파일업로드
					CommonUtil.setInUserInfo(multiRequest, fileVO);
					fileService.insertFile(fileVO);
				}
			}
		}
		
		return json;
	}

	@Override
	public JSONObject mysqlDelete(MysqlVO paramVO) throws Exception{
		JSONObject json = new JSONObject();
		
		mysqlDAO.mysqlDelete(paramVO);
		
		// 첨부파일삭제
		FileVO fileVO = new FileVO();
		fileVO.setUpload_group(FILE_UPLOAD_GROUP);
		fileVO.setMaster_id(paramVO.getSample_id());
		fileService.deleteAllFileList(fileVO);
		
		return json;
	}
	
	@Override
	public List<Map<String, Object>> excelDownload(MysqlVO paramVO) throws Exception{
		return mysqlDAO.excelDownload(paramVO);
	}

	@Override
	public JSONObject mysqlExcelUpload(MultipartHttpServletRequest multiRequest, MysqlVO paramVO) throws Exception {
		JSONObject json = new JSONObject();
		
		Map<String, MultipartFile> mFiles = multiRequest.getFileMap();

		// 에러라인 표시
		int errorRow = 0;
		int queryRow = 0;
		
		FileVO fileVO = new FileVO();
		
		FileVO asisFileVO = new FileVO();
		String orginFileNm = "";
		
		try {
			
			//파일정보
			if (!mFiles.isEmpty()) {
				
				// 업로드 명부파일 저장
				List<FileVO> result = FileUtil.parseFileInf(mFiles, FILE_PREFIX, FILE_STORE_PATH, EXCEL_UPLOAD_PATH);
				if(result != null && !result.isEmpty()){
					for(int i=0; i<result.size(); i++){
						fileVO = result.get(i);
						orginFileNm = fileVO.getOrg_file_name();
						CommonUtil.setInUserInfo(multiRequest, fileVO);
						fileVO.setUpload_group(UPLOAD_GROUP_SAMPLE_EXCEL);
						// 명부
						fileVO.setUpload_group_sub(UPLOAD_GROUP_SUB_SAMPLE_EXCEL);
						fileVO.setMaster_table(MASTER_TABLE_SAMPLE);
						fileVO.setMaster_id("0");
						fileService.insertFile(fileVO);
					}
				}
			}
			
			String filePath = FILE_STORE_PATH + fileVO.getFile_path()+File.separator+fileVO.getSys_file_name();
			
			// 엑셀파일을 읽어 오기 위한 옵션 
			ExcelReadOption excelReadOption = new ExcelReadOption();
			excelReadOption.setFilePath(filePath);
			
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
			Date currentTime = new Date ();
			logger.info(" 파일 로딩 start  : "+ mSimpleDateFormat.format(currentTime).toString());
			
			// 변환할 데이타 신규 작성
			List<String[]> excelContent = new ArrayList<>();
			
			// 확장자 이용 엑셀 타입 확인
			String fileType = ExcelCsvFileUtil.getFileType(filePath);
			
			// 엑셀 컬럼 수 지정
			excelReadOption.setColLength(10);
			
			// 엑셀 타입별 List(String()) 변환
			if("xlsx".equals(fileType)) {
				excelReadOption.setStartRow(3);
				excelContent = ExcelCsvFileUtil.getExcelBigData(filePath, excelReadOption);
			}else if("xls".equals(fileType)) {
				excelReadOption.setStartRow(3);
				excelContent = ExcelCsvFileUtil.getExcelDefaultData(filePath, excelReadOption);
			} else if("csv".equals(fileType)) {
				excelContent = ExcelCsvFileUtil.getCsvData(filePath);
			} 
			
			// 엑셀데이타 DB에 INSERT하기 위해 리스트 VO 선언
			List<MysqlVO> excelList = new ArrayList<>();
			MysqlVO excelData;
			
			String[] article;
			
//			String[] row0 = new String[excelContent.get(0).length];
//			row0 = excelContent.get(0);
			
			for(int i=2; i<excelContent.size(); i++){
				article = new String[excelContent.get(i).length];
				article = excelContent.get(i);
				errorRow = i;
				
				excelData = new MysqlVO();
				excelData.setSample_name(StringUtil.nvl(article[2]));
				excelData.setSample_subject(StringUtil.nvl(article[3]));
				excelData.setSample_contents(StringUtil.nvl(article[4]));
				excelData.setSample_order(StringUtil.nvl(article[5]));
				excelData.setHot_yn(StringUtil.nvl(article[6]));
				CommonUtil.setInUserInfo(multiRequest, excelData);
				
				excelList.add(excelData);

			}
			
			// 인서트
			currentTime = new Date();
			logger.info("인서트시작: "+mSimpleDateFormat.format(currentTime).toString() + " : " +excelList.size());
			if(CommonUtil.isNotEmpty(excelList)){
				queryRow = mysqlDAO.mysqlInsertExcel(excelList);
				excelContent = null;
				excelList = new ArrayList<>();
			}
			currentTime = new Date();
			logger.info(" QUERY END 시간 : "+ mSimpleDateFormat.format(currentTime).toString());
			
			CommonUtil.getReturnCodeSuc(json);
			logger.info("업로드 : 총 업로드 라인수"+errorRow);
		} catch (Exception e) {
			logger.info("EXCEPTION:mysqlExcelUpload"+e.toString());
			e.printStackTrace();
			logger.info("EXCEPTION 업로드 queryRow = ", queryRow);
			logger.info("EXCEPTION ErrorRow = ", errorRow);
			
			// 기존파일 삭제 롤백처리 및 등록된 파일 삭제처리
			// 모든파일 삭제
			CommonUtil.setUpUserInfo(multiRequest, asisFileVO);
			fileService.deleteFileList(asisFileVO);
			
			json.put("errorRow", errorRow);
			json.put("exceptionMsg", e.toString());
			CommonUtil.getReturnCodeFail(json);
		} finally {
			logger.info("EXCEL 파일 삭제 ",json.toString());
			FileUtil.deleteFileInf(fileVO.getSys_file_name(), FILE_STORE_PATH, EXCEL_UPLOAD_PATH);
		}

		logger.info("mysqlExcelUpload Imple:"+json.toString());
		return json;
	}

}
