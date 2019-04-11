package com.neoforth.sample.recaptcha;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.neoforth.sample.mysql.vo.MysqlVO;

/**
 * Handles requests for the application home page.
 */
@Controller("sample.recaptcha.captchaController")
public class CaptchaController {
	
	private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);
	
	/**
	 * 구글 recapcha ajax 서버활용
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/sample/captcha/captchaList", method = RequestMethod.GET)
	public ModelAndView captchaList(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("sample/captcha/captchaList");
		
		return mav;
	}
	
	/**
	 * 구글 recapcha ajax 서버활용
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sample/captcha/verifyRecaptcha", method = RequestMethod.POST)
	public int VerifyRecaptcha(HttpServletRequest request) {
		VerifyRecaptcha.setSecretKey("6LfIepsUAAAAABHrgt75E-PmHXQMPBpEV392GFls");
		String gRecaptchaResponse = request.getParameter("recaptcha");
		System.out.println(gRecaptchaResponse);
		//0 = 성공, 1 = 실패, -1 = 오류
		try {
			if(VerifyRecaptcha.verify(gRecaptchaResponse))
				return 0;
			else return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 캡챠 jsp 단에서 서브밋으로 끝내는거
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/sample/captcha/recaptchaList", method = RequestMethod.GET)
	public ModelAndView recaptchaList(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("sample/captcha/recaptchaList");
		
		return mav;
	}
	
	/**
	 * 심플캡챠
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/sample/captcha/simpleCaptcha", method = RequestMethod.GET)
	public ModelAndView simpleCaptcha(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("sample/captcha/simpleCaptcha");
		
		return mav;
	}
	
	/**
	 * 심플캡챠
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/sample/captcha/simpleCaptchaSubmit", method = RequestMethod.POST)
	public ModelAndView simpleCaptchaSubmit(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("sample/captcha/simpleCaptchaSubmit");
		
		return mav;
	}
	
}
