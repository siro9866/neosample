package com.neoforth.sample.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.neoforth.sample.mysql.vo.MysqlVO;

@RestController("sample.api.jusoController")
public class JusoController {

	private static final Logger logger = LoggerFactory.getLogger(JusoController.class);

	@Value("#{config['JUSO_API_KEY_POP']}") String JUSO_API_KEY_POP;
	@Value("#{config['JUSO_API_KEY_SEARCH']}") String JUSO_API_KEY_SEARCH;

	@RequestMapping(value = "/sample/api/juso/jusoAPI", method = RequestMethod.GET)
	public ModelAndView jusoAPI(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("sample/api/juso/jusoAPI");

		return mav;
	}

	/**
	 * 팝업
	 * 
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sample/api/juso/jusoPopup")
	public ModelAndView jusoPopup(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("JUSO_API_KEY", JUSO_API_KEY_POP);
		mav.setViewName("sample/api/juso/jusoPopup");

		return mav;
	}

	/**
	 * 검색으로 하기
	 * 
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sample/api/juso/jusoAPISearch", method = RequestMethod.GET)
	public ModelAndView jusoAPISearch(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("JUSO_API_KEY", JUSO_API_KEY_SEARCH);
		mav.setViewName("sample/api/juso/jusoAPISearch");

		return mav;
	}

	/**
	 * 주소검색 조회 : xml
	 * 
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample/api/juso/getAddrApiXML")
	public void getAddrApiXML(HttpServletRequest req, ModelMap model, HttpServletResponse response) throws Exception {
		// 요청변수 설정
		String currentPage = req.getParameter("currentPage"); // 요청 변수 설정 (현재 페이지. currentPage : n > 0)
		String countPerPage = req.getParameter("countPerPage"); // 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100)
		String confmKey = req.getParameter("confmKey"); // 요청 변수 설정 (승인키)
		String keyword = req.getParameter("keyword"); // 요청 변수 설정 (키워드)
		// OPEN API 호출 URL 정보 설정
		String apiUrl = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage=" + currentPage + "&countPerPage="
				+ countPerPage + "&keyword=" + URLEncoder.encode(keyword, "UTF-8") + "&confmKey=" + confmKey;
		URL url = new URL(apiUrl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String tempStr = null;

		while (true) {
			tempStr = br.readLine();
			if (tempStr == null)
				break;
			sb.append(tempStr); // 응답결과 XML 저장
		}
		br.close();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		response.getWriter().write(sb.toString()); // 응답결과 반환
		logger.info("RETURN:" + sb.toString());
	}

	/**
	 * 주소검색 조회 : json
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample/api/juso/getAddrApiJSON")
	public ModelAndView getAddrApiJSON(HttpServletRequest req, ModelMap model, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();
		JSONObject json = new JSONObject();
		mav.setViewName("jsonView");
		
		// 요청변수 설정
		String currentPage = req.getParameter("currentPage"); // 요청 변수 설정 (현재 페이지. currentPage : n > 0)
		String countPerPage = req.getParameter("countPerPage"); // 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100)
		String resultType = req.getParameter("resultType"); // 요청 변수 설정 (검색결과형식 설정, json)
		String confmKey = req.getParameter("confmKey"); // 요청 변수 설정 (승인키)
		String keyword = req.getParameter("keyword"); // 요청 변수 설정 (키워드)
		// OPEN API 호출 URL 정보 설정
		String apiUrl = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage=" + currentPage + "&countPerPage="
				+ countPerPage + "&keyword=" + URLEncoder.encode(keyword, "UTF-8") + "&confmKey=" + confmKey
				+ "&resultType=" + resultType;
		URL url = new URL(apiUrl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String tempStr = null;

		while (true) {
			tempStr = br.readLine();
			if (tempStr == null)
				break;
			sb.append(tempStr); // 응답결과 JSON 저장
		}
		br.close();
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/xml");
//		response.getWriter().write(sb.toString()); // 응답결과 반환
		logger.info("RETURN:" + sb.toString());
		
		json = (JSONObject) JSONValue.parse(sb.toString());
		
		mav.addObject("resultJson", json);
		
		return mav;
		
	}

}
