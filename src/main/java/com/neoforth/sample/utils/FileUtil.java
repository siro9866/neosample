package com.neoforth.sample.utils;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neoforth.sample.common.vo.FileVO;


public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private static String allow_file;
	private static String deny_file;
	
	public static String getFileExtension(String fileName) {
		String[] file = fileName.split("[.]");
		logger.debug("getFileExtension" + file.length);
		return (file.length > 1) ? file[1] : "";
	}

	/**
	 * 단 건 첨부파일 등록시 파일정보 (폼에 파일이 하나인 경우)
	 * 파일명은 접두어 + yyyyMMddhhmmssSSS
	 * @param file
	 * @param KeyStr	파일접두어
	 * @param fileStorePath	파일저장 루트경로
	 * @param storePath	파일저장 추가경로
	 * @return
	 * @throws Exception
	 */
	public static FileVO parseFileInf(MultipartFile file, String KeyStr, String fileStorePath, String storePath)
			throws Exception {
		if (CommonUtil.isEmpty(file)) {
			return null;
		}
		String orginFileName = file.getOriginalFilename();
		// --------------------------------------
		// 원 파일명이 없는 경우 처리
		// (첨부가 되지 않은 input file type)
		// --------------------------------------
		if (CommonUtil.isEmpty(orginFileName)) { return null; }

		logger.debug("FILESTOREPATH:" + fileStorePath);
		logger.debug("STOREPATH:" + storePath);
		
		String savePath = filePathBlackList(fileStorePath + storePath);
		File saveFolder = new File(savePath);

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		int index = orginFileName.lastIndexOf(".");
		String fileExt = orginFileName.substring(index + 1);
		String newName = KeyStr + getTimeStamp() + "." + fileExt;
		long size = file.getSize();
		String filePath = savePath + "/" + newName;
		logger.debug("filePath" + filePath);
		file.transferTo(new File(filePath));

		FileVO fleVO = new FileVO();
		fleVO.setFile_ext(fileExt);	
		fleVO.setFile_path(storePath);
		fleVO.setFile_size(String.valueOf(size));
		fleVO.setOrg_file_name(orginFileName);
		fleVO.setSys_file_name(newName);
		
		return fleVO;
	}
	
	/**
	 * 다중 건 첨부파일 등록시 파일정보  (폼에 파일이 하나이상 경우)
	 * 파일명은 접두어 + yyyyMMddhhmmssSSS + seq(멀티일경우)
	 * @param files
	 * @param KeyStr	파일접두어
	 * @param fileStorePath	파일저장 루트경로
	 * @param storePath	파일저장 추가경로
	 * @return
	 * @throws Exception
	 */
	public static List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, String fileStorePath, String storePath) throws Exception {
		if (CommonUtil.isEmpty(files)) {
			return null;
		}
		int fileKey = 0;

		String savePath = filePathBlackList(fileStorePath + storePath);
		File saveFolder = new File(savePath);

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fleVO = new FileVO();

		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();
			
			file = entry.getValue();
			String orginFileName = file.getOriginalFilename();

			// --------------------------------------
			// 원 파일명이 없는 경우 처리
			// (첨부가 되지 않은 input file type)
			// --------------------------------------
			if (CommonUtil.isEmpty(orginFileName)) {
				continue;
			}

			int index = orginFileName.lastIndexOf(".");
			String fileExt = orginFileName.substring(index + 1);

			String newName = "";
			newName = KeyStr + getTimeStamp() + fileKey + "." + fileExt;

			logger.debug("entry.getKey():"+entry.getKey());
			logger.debug("KeyStr:"+KeyStr);
			logger.debug("newName:"+newName);
			
			long size = file.getSize();

			filePath = savePath + "/" + newName;
			file.transferTo(new File(filePathBlackList(filePath)));

			fleVO = new FileVO();
			fleVO.setFile_ext(fileExt);	
			fleVO.setFile_path(storePath);
			fleVO.setFile_size(String.valueOf(size));
			fleVO.setOrg_file_name(orginFileName);
			fleVO.setSys_file_name(newName);
			fleVO.setElement_nm(entry.getKey());
			result.add(fleVO);

			fileKey++;
		}

		if (result.size() == 0) {
			result = null;
		}
		return result;
	}	
	
	private static String filePathBlackList(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

		return returnValue;
	}

	/**
	 * 파일명으로 사용하기 위해 밀리세컨드까지
	 * @return
	 */
	private static String getTimeStamp() {
		String rtnStr = null;
		String pattern = "yyyyMMddhhmmssSSS";
		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		rtnStr = sdfCurrent.format(ts.getTime());
		return rtnStr;
	}
	
	
	public static boolean checkFileExtension(MultipartHttpServletRequest multiRequest) {

		String file_extension;
		String filename = "";
		int rtn = 0;
		// String deny_file_extension="jsp,asp,php,java,sh";
		// String
		// allow_file_extension="txt,doc,docx,ppt,pptx,xls,xlsx,hwp,pdf,png,gif,jpeg";
		// logger.debug("### checkFileExtension
		// allow_file:["+allow_file+"]"+multiRequest.getContentType());

		List<String> allowFileExtension = Arrays.asList(allow_file.split(","));
		List<String> denyFileExtension = Arrays.asList(deny_file.split(","));

		Iterator<String> names = multiRequest.getFileNames();

		// logger.debug("### checkFileExtension
		// multiRequest.getFileMap().size():["+multiRequest.getFileMap().size()+"]");

		if (multiRequest.getFileMap().size() == 0) {
			rtn++;
		}
		while (names.hasNext()) {
			filename = (String) names.next();
			// logger.debug("### checkFileExtension filename:["+filename+"]");
			List<MultipartFile> files3 = multiRequest.getFiles(filename);

			for (MultipartFile mfile : files3) {
				// logger.debug("### checkFileExtension
				// ["+mfile.getName()+"],OriginalFilename:["+mfile.getOriginalFilename()+"],file
				// Extension["+FilenameUtils.getExtension(mfile.getOriginalFilename())+"]");
				if (mfile.getOriginalFilename() == null || mfile.getOriginalFilename().equals("")) {
					rtn++;
				}

				file_extension = FilenameUtils.getExtension(mfile.getOriginalFilename());
				if (allowFileExtension.size() > 0 && allowFileExtension.contains(file_extension.toLowerCase())) {
					// logger.debug("### checkFileExtension"+(new
					// StringBuilder(String.valueOf(file_extension))).append("
					// file type is allowed to be uploaded!").toString());
					rtn++;
				}
				if (denyFileExtension.size() > 0 && denyFileExtension.contains(file_extension.toLowerCase())) {
					// logger.debug("### checkFileExtension"+(new
					// StringBuilder(String.valueOf(file_extension))).append("
					// file type is not allowed to be uploaded!").toString());
					rtn = -2;
				}
			}
			logger.debug("### checkFileExtension rtn :" + rtn);
		}
		logger.debug("### checkFileExtension b_rtn:[" + rtn + "]");
		return (rtn > 0) ? true : false;
	}

	public static void deleteFileInf(String sysFileName, String fileStorePath, String storePath) throws Exception {
		String savePath = filePathBlackList(fileStorePath + storePath +"/"+ sysFileName);
		File saveFolder = new File(savePath);
		saveFolder.delete();
	}
	
	
}
