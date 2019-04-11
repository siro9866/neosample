package com.neoforth.sample.common.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neoforth.sample.common.vo.FileVO;

@Repository("sample.common.fileDAO")
public class FileDaoImpl implements FileDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<FileVO> getFileList(FileVO fileVO) throws Exception {
		return sqlSession.selectList("sample.common.file.getFileList", fileVO);
	}
	
	@Override
	public FileVO getFile(FileVO fileVO) throws Exception {
		return sqlSession.selectOne("sample.common.file.getFile", fileVO);
	}

	@Override
	public int insertFile(FileVO fileVO) throws Exception {
		return sqlSession.insert("sample.common.file.insertFile", fileVO);
	}

	@Override
	public int deleteFile(FileVO fileVO) throws Exception {
		return sqlSession.update("sample.common.file.deleteFile", fileVO);
	}
	
	@Override
	public int deleteCancelFile(FileVO fileVO) throws Exception {
		return sqlSession.update("sample.common.file.deleteCancelFile", fileVO);
	}
	
	@Override
	public int deleteAllFileList(FileVO fileVO) throws Exception {
		return sqlSession.update("sample.common.file.deleteAllFileList", fileVO);
	}
	
	@Override
	public int deleteFileList(FileVO fileVO) throws Exception {
		return sqlSession.update("sample.common.file.deleteFileList", fileVO);
	}

}
