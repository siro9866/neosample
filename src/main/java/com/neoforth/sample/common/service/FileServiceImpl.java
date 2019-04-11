package com.neoforth.sample.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.neoforth.sample.common.dao.FileDaoImpl;
import com.neoforth.sample.common.vo.FileVO;


@Service("sample.common.fileService")
public class FileServiceImpl implements FileService{

	private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Resource(name="sample.common.fileDAO")
	private FileDaoImpl fileDAO;

	@Override
	public List<FileVO> getFileList(FileVO fileVO) throws Exception {
		return fileDAO.getFileList(fileVO);
	}
	
	@Override
	public FileVO getFile(FileVO fileVO) throws Exception {
		return fileDAO.getFile(fileVO);
	}

	@Override
	public int insertFile(FileVO fileVO) throws Exception {
		return fileDAO.insertFile(fileVO);
	}

	@Override
	public int deleteFile(FileVO fileVO) throws Exception {
		return fileDAO.deleteFile(fileVO);
	}
	
	@Override
	public int deleteCancelFile(FileVO fileVO) throws Exception {
		return fileDAO.deleteCancelFile(fileVO);
	}
	
	@Override
	public int deleteAllFileList(FileVO fileVO) throws Exception {
		return fileDAO.deleteAllFileList(fileVO);
	}
	
	@Override
	public int deleteFileList(FileVO fileVO) throws Exception {
		return fileDAO.deleteFileList(fileVO);
	}

}
