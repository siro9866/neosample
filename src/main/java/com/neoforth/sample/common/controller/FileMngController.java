package com.neoforth.sample.common.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.neoforth.sample.common.service.FileService;
import com.neoforth.sample.common.vo.FileVO;
import com.neoforth.sample.utils.CommonUtil;
import com.neoforth.sample.utils.FileUtil;


@Controller
@RequestMapping("/sample/common")
public class FileMngController{
	private static final Logger logger = LoggerFactory.getLogger(FileMngController.class);

	@Resource(name="sample.common.fileService")
	private FileService fileService;
	
	@Value("#{config['SERVER_URL']}") String SERVER_URL;
	@Value("#{config['EDITOR_UPLOAD_ABLE_FILE']}") String EDITOR_UPLOAD_ABLE_FILE;
	@Value("#{config['FILE_EDITOR_PATH']}") String FILE_EDITOR_PATH;
	@Value("#{config['FILE_STORE_PATH']}") String FILE_STORE_PATH;
	@Value("#{config['EDITOR_IMGSTORE_PATH']}") String EDITOR_IMGSTORE_PATH;
	@Value("#{config['UPLOAD_VIEWPATH']}") String UPLOAD_VIEWPATH;
	@Value("#{config['EDITOR_FILE_PREFIX']}") String EDITOR_FILE_PREFIX;
	@Value("#{config['SERVER_URL_DEV']}") String SERVER_URL_DEV;
	@Value("#{config['SERVER_URL_LOCAL']}") String SERVER_URL_LOCAL;
	
	
	/**
	 * 에디터 이미지 삽입시 업로드
	 * @param multiRequest
	 * @param model
	 * @param response
	 * @param locale
	 * @throws Exception
	 */
	@RequestMapping(value="/editorfileUpload", method=RequestMethod.POST)
	public void editorfileUpload(	final MultipartHttpServletRequest multiRequest, Model model ,HttpServletResponse response, Locale locale)
			throws Exception {
		JSONObject retMap = new JSONObject();
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter writer = response.getWriter();

		Iterator<String> fileIterator = multiRequest.getFileNames();
		
		while (fileIterator.hasNext()) {
			MultipartFile mFile = multiRequest.getFile((String) fileIterator.next());
		
			logger.debug(mFile.getContentType());
			logger.debug(mFile.getOriginalFilename());
			String fileExt = FileUtil.getFileExtension(mFile.getOriginalFilename());
			logger.debug(fileExt +"/"+EDITOR_UPLOAD_ABLE_FILE);
			if(EDITOR_UPLOAD_ABLE_FILE.indexOf(fileExt.toLowerCase())==-1){
				CommonUtil.getReturnCodeFail(retMap, "허가되지 않은 파일 형식입니다.");
			}else{
				if (mFile.getSize() > 0) {	
					FileVO fileVO = new FileVO();
					fileVO = FileUtil.parseFileInf(mFile, EDITOR_FILE_PREFIX, FILE_EDITOR_PATH, EDITOR_IMGSTORE_PATH);

					// 에디터로 돌려줘야 할 값
					retMap.put("uploaded", multiRequest.getParameter("CKEditorFuncNum") == null? 1 : multiRequest.getParameter("CKEditorFuncNum"));
					retMap.put("uploaded", 1);
					retMap.put("fileName",fileVO.getSys_file_name());
					
					// 서버 URL 적용
					if(CommonUtil.getServerName().equals("REAL")){
					}else if(CommonUtil.getServerName().equals("DEV")){
						SERVER_URL = SERVER_URL_DEV;
					}else{
						SERVER_URL = SERVER_URL_LOCAL;
					}
					
					
					retMap.put("url",SERVER_URL+UPLOAD_VIEWPATH+fileVO.getFile_path()+"/"+fileVO.getSys_file_name());
					
					
				}
			}
		} 
		logger.debug(retMap.toString());
		writer.print(retMap.toString());
	}

	/**
	 * 파일 다운로드
	 * @param fileVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/fileDownLoad", method=RequestMethod.POST)
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
		
		/*if(!FILE_STORE_PATH.matches(fileVO.getFile_path())){
			logger.warn("불법 파일접근 : ", request.getRemoteAddr(), fileVO.getFile_path());
			response.sendRedirect("/");
			return;
		}*/
		
		File uFile = new File(FILE_STORE_PATH + fileVO.getFile_path()+File.separator+fileVO.getSys_file_name());
		int fSize = (int) uFile.length();

		logger.debug("FILE DOWNLOAD:"+fSize);
		logger.debug(FILE_STORE_PATH);
		logger.debug(fileVO.getFile_path());
		logger.debug(fileVO.getSys_file_name());
		logger.debug(fileVO.getOrg_file_name());
		
		if (fSize > 0) {
			String mimetype = "application/x-msdownload";
			
			
//			String browser = getBrowser(request);
//			if (browser.equals("IOS")) {
				int e = fileVO.getSys_file_name().lastIndexOf(".");
				int p = Math.max(fileVO.getSys_file_name().lastIndexOf("/"), fileVO.getSys_file_name().lastIndexOf("\\"));
				String extension = "";
				if(e > p){
					extension = fileVO.getSys_file_name().substring(e + 1);
					if("pdf".matches(extension.toLowerCase())){
						logger.info("MATCH OK:pdf");
						mimetype = "application/pdf";
					}else if("ppt".matches(extension.toLowerCase()) || "pptx".matches(extension.toLowerCase())){
						logger.info("MATCH OK:ppt or pptx");
						mimetype = "application/vnd.ms-powerpoint";
					}else if("xls".matches(extension.toLowerCase()) || "xlsx".matches(extension.toLowerCase())){
						logger.info("MATCH OK:xls or xlsx");
						mimetype = "application/vnd.ms-excel";
					}else if("doc".matches(extension.toLowerCase()) || "docx".matches(extension.toLowerCase())){
						logger.info("MATCH OK:doc or docs");
						mimetype = "application/msword";
					}else if("jpg".matches(extension.toLowerCase()) || "jpeg".matches(extension.toLowerCase())){
						logger.info("MATCH OK:jpg or jpeg");
						mimetype = "image/jpg";
					}
				}
//			}
			
			response.setContentType(mimetype);
			setDisposition(fileVO.getOrg_file_name(), request, response);
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
	
	/**
	 * 파일삭제
	 * @param fileVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFile", method=RequestMethod.POST)
	public ModelAndView fileDelete(@ModelAttribute FileVO fileVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject json = new JSONObject();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		try {
			fileService.deleteFile(fileVO);
			CommonUtil.getReturnCodeSuc(json);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("PROGRAM_Exception:"+e);
			CommonUtil.getReturnCodeFail(json);
			e.printStackTrace();
		}
		
		logger.debug(json.toString());
		mav.addObject("resultJson", json);
		return mav;
	}

	public void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		logger.debug("BROWSER:"+browser);
		
		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Trident")) { 
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("IOS")) {
			encodedFilename = "\"" + new String(filename.getBytes("ksc5601"), "euc-kr") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			throw new IOException("Not supported browser");
		}

		logger.info("인코딩파일:"+encodedFilename);
		
		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}
	
	public String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		
		logger.debug("User-Agent:" + header);
		
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Trident") > -1) {
			return "Trident";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		} else if (header.indexOf("Safari") > -1 || header.indexOf("iPad") > -1 || header.indexOf("iPhone") > -1 || header.indexOf("iPod") > -1) {
			return "IOS";
		}
		return "Firefox";
	}
}
