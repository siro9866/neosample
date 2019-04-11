package com.neoforth.sample.mysql.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neoforth.sample.mysql.vo.MysqlVO;

import net.sf.json.JSONObject;

public interface MysqlService {
	
	public int mysqlListCnt(MysqlVO paramVO) throws Exception;
	public List<MysqlVO> mysqlList(MysqlVO paramVO) throws Exception;
	public MysqlVO mysqlView(MysqlVO paramVO) throws Exception;
	public JSONObject mysqlInsert(MultipartHttpServletRequest multiRequest, MysqlVO paramVO) throws Exception;
	public JSONObject mysqlUpdate(MultipartHttpServletRequest multiRequest, MysqlVO paramVO) throws Exception;
	public JSONObject mysqlDelete(MysqlVO paramVO) throws Exception;
	public List<Map<String, Object>> excelDownload(MysqlVO paramVO) throws Exception;
	public JSONObject mysqlExcelUpload(MultipartHttpServletRequest multiRequest, MysqlVO paramVO) throws Exception;
	
}
