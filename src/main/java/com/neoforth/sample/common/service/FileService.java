package com.neoforth.sample.common.service;

import java.util.List;

import com.neoforth.sample.common.vo.FileVO;


public interface FileService {
	
	
	public List<FileVO> getFileList(FileVO fileVO) throws Exception;
	public FileVO getFile(FileVO fileVO) throws Exception;
	public int insertFile(FileVO fileVO) throws Exception;
	public int deleteFile(FileVO fileVO) throws Exception;
	public int deleteCancelFile(FileVO fileVO) throws Exception;
	public int deleteAllFileList(FileVO fileVO) throws Exception;
	public int deleteFileList(FileVO fileVO) throws Exception;
	
}
