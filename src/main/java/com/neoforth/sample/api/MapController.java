package com.neoforth.sample.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.neoforth.sample.mysql.vo.MysqlVO;

@RestController("sample.api.mapController")
public class MapController {

	private static final Logger logger = LoggerFactory.getLogger(MapController.class);

	@Value("#{config['JUSO_API_KEY_POP']}") String JUSO_API_KEY_POP;
	@Value("#{config['JUSO_API_KEY_SEARCH']}") String JUSO_API_KEY_SEARCH;

	@RequestMapping(value = "/sample/api/map/mapList", method = RequestMethod.GET)
	public ModelAndView mapList(@ModelAttribute MysqlVO paramVO, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("sample/api/map/mapList");

		return mav;
	}


}
