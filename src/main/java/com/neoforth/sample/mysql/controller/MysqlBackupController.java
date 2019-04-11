package com.neoforth.sample.mysql.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.neoforth.sample.mysql.service.MysqlService;
import com.neoforth.sample.mysql.vo.MysqlVO;
import com.neoforth.sample.utils.CommonUtil;

import net.sf.json.JSONObject;

@Controller("sample.mysqlBackupController")
@RequestMapping(value = "/sample/mysql")
public class MysqlBackupController {

	private static final Logger logger = LoggerFactory.getLogger(MysqlBackupController.class);

	@Resource(name = "sample.mysqlService")
	private MysqlService mysqlService;

	@RequestMapping(value = "/backupList")
	public ModelAndView backupList(@ModelAttribute MysqlVO paramVO, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/mysql/backupList");
		return mav;
	}

	private static String ID = "neoforth";
	private static String PW = "spdhvhtm";
	private static String DB_NAME = "neoforthDB";
	private static String FILE_PATH = "C://MysqlBackup/";
	private static String FILE_NAME = "BACKUP";
	private static String MYSQL_PATH = "C://Program Files/MySQL/MySQL Server 8.0/bin/";
	// private static String FILE_PATH = "/home/falinux/Temp/"; // 리눅스 패스

	/**
	 * MYSQL 데이타베이스 백업
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/backupStart")
	public ModelAndView backupStart(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		JSONObject json = new JSONObject();

		// 백업 패스파일명
		StringBuffer bakupFileNm = new StringBuffer();

		// 백업파일 경로 없으면 생성하라
		File saveFolder = new File(FILE_PATH);
		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}
		bakupFileNm.append(FILE_PATH);
		bakupFileNm.append(FILE_NAME);
		bakupFileNm.append("_");
		bakupFileNm.append(getDateTime());
		bakupFileNm.append(".sql");
		// 백업 실행
		logger.info("파일명:" + bakupFileNm.toString());
		
		String executeCmd = MYSQL_PATH + "mysqldump -u " + ID + " -p" + PW + " --add-drop-database -B " + DB_NAME + " -r " + bakupFileNm.toString();
		Process runtimeProcess;
		try {

			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0) {
				logger.info("백업 성공");
				CommonUtil.getReturnCodeSuc(json);
			} else {
				logger.info("백업 실패");
				CommonUtil.getReturnCodeFail(json);
			}
		} catch (Exception ex) {
			logger.info("백업 실패");
			ex.printStackTrace();
			CommonUtil.getReturnCodeFail(json, ex.toString());
		}
		
		mav.addObject("result", json);

		logger.info(json.toString());
		
		return mav;
	}
	
	/**
	 * MYSQL 롤백(경로및 파일명 맞추고 오리는 곳)
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/restoreStart")
	public ModelAndView url_restoreStart(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		JSONObject json = new JSONObject();
		
		Scanner sc = new Scanner(paramVO.getSearchValue());
		String fileName = "";
		while ("".equals(fileName)) {
			System.out.print("파일명을 입력하세요 : ");
			fileName = sc.nextLine();
		}
		logger.info("파일명:"+fileName);
		// 복구실행
		String cmd[] = { MYSQL_PATH + "mysql", DB_NAME, "-u" + ID, "-p" + PW, "-e", "source " + FILE_PATH + fileName };
		Process pross = null;
		try {
			Runtime run = Runtime.getRuntime();
			pross = run.exec(cmd);
			
			if (pross.waitFor() == 0) {
				logger.info("복구성공");
				CommonUtil.getReturnCodeSuc(json);
			} else {
				logger.info("복구실패:"+pross.waitFor());
				CommonUtil.getReturnCodeFail(json);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("복구실패");
			CommonUtil.getReturnCodeFail(json, e.toString());
		}
	
		mav.addObject("result", json);
		
		logger.info(json.toString());
		
		return mav;
	}

	/**
	 * 시간 취득
	 * 
	 * @return
	 */
	public static String getDateTime() {
		// 캘린더 인스턴스 취득
		Calendar calendar = Calendar.getInstance();
		// 날짜 취득
		Date date = calendar.getTime();
		// 날짜 형식을 yyyyMMddHHmmss로 날짜 취득
		String dateTime = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));

		return dateTime;
	}

}