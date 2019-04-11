package com.neoforth.sample.etc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.neoforth.sample.mysql.vo.MysqlVO;


@Controller("sample.etcController")
@RequestMapping(value="/sample/etc")
public class EtcController{

	private static final Logger logger = LoggerFactory.getLogger(EtcController.class);
	
	@RequestMapping(value = "/jsonJsp")
	public ModelAndView backupList(@ModelAttribute MysqlVO paramVO, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/etc/jsonJsp");
		return mav;
	}

	
}