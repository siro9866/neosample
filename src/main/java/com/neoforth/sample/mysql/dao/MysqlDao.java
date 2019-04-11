package com.neoforth.sample.mysql.dao;

import java.util.List;
import java.util.Map;

import com.neoforth.sample.mysql.vo.MysqlVO;


public interface MysqlDao {
	
	public int mysqlListCnt(MysqlVO paramVO) throws Exception;
	public List<MysqlVO> mysqlList(MysqlVO paramVO) throws Exception;
	public MysqlVO mysqlView(MysqlVO paramVO) throws Exception;
	public int mysqlInsert(MysqlVO paramVO) throws Exception;
	public int mysqlUpdate(MysqlVO paramVO) throws Exception;
	public int mysqlDelete(MysqlVO paramVO) throws Exception;
	public List<Map<String, Object>> excelDownload(MysqlVO paramVO) throws Exception;
	public int mysqlInsertExcel(List<MysqlVO> paramVO) throws Exception;
	
}
