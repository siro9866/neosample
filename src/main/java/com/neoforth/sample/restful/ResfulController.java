package com.neoforth.sample.restful;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.neoforth.sample.mysql.vo.MysqlVO;
import com.neoforth.sample.utils.CommonUtil;

import net.sf.json.JSONObject;

@RestController("sample.restful.resfulController")
public class ResfulController {

	private static final Logger logger = LoggerFactory.getLogger(ResfulController.class);

	@RequestMapping(value = "/sample/restful/restfulList", method = RequestMethod.GET)
	public ModelAndView restfulList(@ModelAttribute MysqlVO paramVO, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("sample/restful/restfulList");

		return mav;
	}

	/**
	 * VO 리턴
	 * 
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sample/restful/restfulJson")
	public MysqlVO restfulJson(@ModelAttribute MysqlVO paramVO, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		logger.info(paramVO.toStringVo());
		MysqlVO mysqlVO = new MysqlVO();
		mysqlVO.setSample_id("123");
		mysqlVO.setSample_name("JSON 스트링");
		mysqlVO.setDel_yn("N");
		mysqlVO.setSearchValue(paramVO.getSearchValue());
		logger.info(mysqlVO.toStringVo());

		return mysqlVO;
	}

	/**
	 * Map 으로 리턴
	 * 
	 * @param paramVO
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sample/restful/restfulJsonMap")
	public JSONObject restfulJsonMap(@ModelAttribute MysqlVO paramVO, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		JSONObject json = new JSONObject();
		Map<String, String> resultMap = new HashMap<String, String>();
		CommonUtil.getReturnCodeFail(json);

		try {
			if (CommonUtil.isEmpty(paramVO.getSearchValue())) {
				CommonUtil.getReturnCodeFail(json, "필수항목 누락");
				// Exception 발생이 필요할 경우
				// throw new Exception("에러다 멍충멍충");
			} else {
				resultMap.put("sample_id", "0101010");
				resultMap.put("sample_name", "JSON 스트링 MAP");
				resultMap.put("searchValue", paramVO.getSearchValue());
				json.put("result", resultMap);
				CommonUtil.getReturnCodeSuc(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtil.getReturnCodeFail(json, e.toString());
		}

		return json;
	}

	/**
	 * XML 리턴
	 * @return
	 */
	@RequestMapping(value = "/sample/restful/restfulXML")
	public EmployeeListVO restfulXML() {
		EmployeeListVO employees = new EmployeeListVO();

		EmployeeVO empOne = new EmployeeVO(1, "Lokesh", "Gupta", "howtodoinjava@gmail.com");
		EmployeeVO empTwo = new EmployeeVO(2, "Amit", "Singhal", "asinghal@yahoo.com");
		EmployeeVO empThree = new EmployeeVO(3, "Kirti", "Mishra", "kmishra@gmail.com");

		employees.getEmployee().add(empOne);
		employees.getEmployee().add(empTwo);
		employees.getEmployee().add(empThree);

		return employees;
	}

	/**
	 * 해당 아이디의 XML 리턴
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sample/restful/restfulXML/{id}")
	public ResponseEntity<EmployeeVO> restfulXMLId(@PathVariable("id") int id) {
		if (id <= 3) {
			EmployeeVO employee = new EmployeeVO(1, "Lokesh", "Gupta", "howtodoinjava@gmail.com");
			return new ResponseEntity<EmployeeVO>(employee, HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

}
