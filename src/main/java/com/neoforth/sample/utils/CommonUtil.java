package com.neoforth.sample.utils;

import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neoforth.sample.common.vo.ComVO;

import net.sf.json.JSONObject;

public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 성공
	 * @param json
	 */
	public static void getReturnCodeSuc(JSONObject json) {
		json.put("rCode", "0000");
		json.put("rMsg", "완료되었습니다.");
	}

	/**
	 * 성공이며 특별한 메세지가 필요한 경우
	 * @param json
	 * @param rMsg
	 */
	public static void getReturnCodeSuc(JSONObject json, String rMsg) {
		json.put("rCode", "0000");
		json.put("rMsg", rMsg);
	}

	/**
	 * 실패
	 * @param json
	 */
	public static void getReturnCodeFail(JSONObject json) {
		json.put("rCode", "9999");
		json.put("rMsg", "처리중 문제가 발생하였습니다.");
	}

	/**
	 * 실패지만 특별한 메세지가 필요한 경우
	 * @param json
	 * @param str
	 */
	public static void getReturnCodeFail(JSONObject json, String str) {
		json.put("rCode", "9999");
		json.put("rMsg", str);
	}

	/**
	 * 결과와 메세지를 각각 설정 할 경우
	 * @param json
	 * @param rCode
	 * @param rMsg
	 */
	public static void getReturnCodeEtc(JSONObject json, String rCode, String rMsg) {
		json.put("rCode", rCode);
		json.put("rMsg", rMsg);
	}

	/**
	 * client ip 정보
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")) {
			clientIp = request.getHeader("REMOTE_ADDR");
		}

		if (null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")) {
			clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}

	public static void setInUserInfo(HttpServletRequest request, ComVO comVO) {
		comVO.setIn_user((String) request.getSession().getAttribute("v_id"));
		try {
			//comVO.setIn_ip(InetAddress.getLocalHost().getHostAddress());
			comVO.setIn_ip(request.getRemoteAddr());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setUpUserInfo(HttpServletRequest request, ComVO comVO) {
		comVO.setUp_user((String) request.getSession().getAttribute("v_id"));
		try {
			comVO.setUp_ip(request.getRemoteAddr());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 페이징시 사용
	public static ComVO setPageRow(ComVO comVO) {
		comVO.setPageEndRow(comVO.getPageNum() * comVO.getPageSize());
		comVO.setPageStartRow(1 + (comVO.getPageNum() - 1) * comVO.getPageSize());
		return comVO;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String)
			return obj == null || "".equals(obj.toString().trim());
		else if (obj instanceof List)
			return obj == null || ((List) obj).isEmpty();
		else if (obj instanceof Map)
			return obj == null || ((Map) obj).isEmpty();
		else if (obj instanceof Object[])
			return obj == null || Array.getLength(obj) == 0;
		else
			return obj == null;
	}

	public static boolean isNotEmpty(Object s) {
		return !isEmpty(s);
	}

	public static void close(Closeable... resources) {
		for (Closeable resource : resources) {
			if (resource != null) {
				try {
					resource.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
	
	public static String generateState() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
	
	/**
	 * size만큼의 random value return
	 * @param size
	 * @return
	 */
	public static String getRandomData(int size){
		Random rd = new Random();
		
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<size;i++){
			int r = rd.nextInt(10);
			sb.append(String.valueOf(r));
		}
		
		return sb.toString();
	}

	/**
	 * 서버 호스트명 가져오기
	 *
	 * @Method Name : getServerName
	 * @작성일 : 2019. 1. 13.
	 * @작성자 : s1212919
	 * @변경이력 : 
	 * @Method 설명 : 
	 * @return
	 * @throws UnknownHostException
	 *
	 */
	public static String getServerName () throws UnknownHostException {
		String result		= "";

		String	hostName		= InetAddress.getLocalHost().getHostName();

		if("VHOST1".equals(hostName) || "VHOST2".equals(hostName)){
			result		= "REAL";
		} else if("WS16E".equals(hostName)) {
			result		= "ACE";
		}else if("dwas01".equals(hostName)){
			result		= "DEV";
		}else{
			result		= "LOCAL";
		}
		
		logger.debug("★★★★★★★★★★  whatServer   ★★★★★★★★★★★★★★");
		logger.debug("hostName:"+hostName);
		logger.debug("★★★★★★★★★★"+result+"★★★★★★★★★★★★★★");
		return result;
	}
	
	
	public static String getNowTime(){
		// 현재시간
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
		Date currentTime = new Date ();
	
		return mSimpleDateFormat.format ( currentTime );
	}
	
	public static String paramDecodeUTF8(String param) {
		
		String result = "";
		if(isNotEmpty(param)){
			
			try {
				result = URLDecoder.decode(param, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.debug("paramDecodeUTF8 E:" + e.toString());
			}
			
		}
		return result;
	}
	
}
