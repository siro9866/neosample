package com.neoforth.sample.utils;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;




public class StringUtil {
	/**
	 * 태그를 태그표현식으로 바꿈
	 * @param str
	 * @return str(String)
	 */
	public static String HTMLDecoder(String str) {
		if(str==null) str="";
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("\r\n", "<br>");
		return str;
	}

	public static String unescape(String s) {
		if (s == null)
			return "";
		String s1 = replace(s, "\\", "");
		return s1;
	}

	/**
	 * 태그표현식을 태그로 바꿈
	 * @param str
	 * @return str(String)
	 */
	public static String HTMLEncoder(String str) {
		if(str==null) str="";
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("<br>", "\r\n");
		return str;
	}

	/**
	 * 엔터를 br 태그로 바꿈
	 * @param str
	 * @return str(String)
	 */
	public static String CarriageReturnDecoder(String str) {
		if(str==null) str="";
		str = str.replaceAll("\r\n", "<br>");
		return str;
	}

    public static String replace(String s, String s1, String s2)
    {
        if(s == null || s1 == null || s2 == null)
            return s;
        StringBuffer stringbuffer = new StringBuffer();
        int i = s1.length();
        int j = 0;
        for(int k = 0; (k = s.indexOf(s1, j)) != -1;)
        {
            stringbuffer.append(s.substring(j, k)).append(s2);
            j = k + i;
        }

        stringbuffer.append(s.substring(j));
        return stringbuffer.toString();
    }


    /**
	 * br 태그를 엔터로 바꿈
	 * @param str
	 * @return str(String)
	 */
	public static String CarriageReturnEncoder(String str) {
		if(str==null) str="";
		str = str.replaceAll("<br>", "\r\n");
		return str;
	}

	/**
	 * 엔터를 없앰
	 * @param str
	 * @return str(String)
	 */
	public static String CarriageReturnDelete(String str) {
		if(str==null) str="";
		str = str.replaceAll("\r\n", "");
		return str;
	}

	/**
	 * 싱글쿼트를 유니코드로 으로 변환
	 * @param str
	 * @return str(String)
	 */
	public static String SingleQuotToUniCode(String str) {
		if(str==null) str="";
		str = str.replaceAll("'", "&#39");
		return str;
	}

	/**
     * null값 또는 빈 문자을 체크해서 지정된 문자열로 대체
     * @param str - 변환할 문자열
     * @param newStr - 변환된 문자열
     * @return 널 또는 빈 문자대체 문자열
     */
    public static String nvl(String str, String newStr) {
        if(str == null || str.trim().equals("")) {
            return newStr;
        } else {
            return str;
        }
    }

    /**
     * BigDecimal의 null값을 체크해서 지정된 BigDecimal의 대체
     * @param bigDecimal
     * @param newBigDecimal
     * @return 널 대체 BigDecimal
     */
    public static BigDecimal nvl(BigDecimal bigDecimal, BigDecimal newBigDecimal) {
    	if(bigDecimal == null) {
    		return newBigDecimal;
    	} else {
    		return bigDecimal;
    	}
    }

    /**
     * null값 또는 빈 문자을 체크해서 지정된 문자열로 대체
     * @param str
     * @return 빈문자 ""
     */
    public static String nvl(String str) {
        if(str == null || str.trim().equals("")) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * BigDecimal의 null값을 체크해서 지정된 BigDecimal의 대체
     * @param bigDecimal
     * @return 널 대체 BigDecimal(0)
     */
    public static BigDecimal nvl(BigDecimal bigDecimal) {
    	if(bigDecimal == null) {
    		return new BigDecimal(0);
    	} else {
    		return bigDecimal;
    	}
    }

    /**
     * null값 또는 빈 숫자를 체크해서 지정된 숫자로 대체
     * @param str - 변환할 숫자
     * @param newStr - 변환된 문자열
     * @return 널 또는 빈 문자대체 문자열
     */
    public static int nvl(Integer iVar, String newStr) {
        if(iVar == null) {
            return new Integer(newStr).intValue();
        } else {
            return iVar;
        }
    }

    /**
     * null값 또는 빈 숫자를 체크해서 지정된 숫자로 대체
     * @param str - 변환할 숫자
     * @param newStr - 변환된 문자열
     * @return 널 또는 빈 문자대체 문자열
     */
    public static long nvl(Long iVar, String newStr) {
        if(iVar == null) {
            return new Long(newStr).longValue();
        } else {
            return iVar;
        }
    }

    /**
     * null값 또는 빈 숫자를 체크해서 지정된 숫자로 대체
     * @param iVar - 변환할 숫자
     * @param newVar - 변환된 숫자
     * @return 널 또는 빈 문자대체 문자열
     */
    public static long nvl(Long iVar, long newVar) {
        if(iVar == null) {
            return new Long(newVar);
        } else {
            return iVar;
        }
    }

    /**
     * 토큰으로 문자열을 추출후 지정한 위치에 있는 문자열을 추출
     * @param str
     * @param token
     * @param position
     * @return String 분리된 문자열
     */
    public static String tokenPosition(String str, String token, int position) {
    	int i = 0;
    	String result = "";
    	StringTokenizer st = new StringTokenizer(str, token);
    	while(st.hasMoreTokens()) {
    		if(i == position) {
    			result = st.nextToken();
    			break;
    		} else {
    			st.nextToken();
    		}
    		i++;
    	}
    	return result;
    }

    /**
     * String 문자열 배열을 구분자를 포함하여 하나의 단일 문자로 변환 후 반환
	 * 예) String[] array = {"aaa","bbb","ccc"}
     * String result = combineStringArray(array,",");
     * return "aaa,bbb,ccc"
     * @param   strArray
     * @param   delim
     * @return  해당 문자열을 조합한 새로운 문자열
     */
    public static String combineStringArray(String[] strArray, String delim) {
        String result = "";
        if (strArray != null) {
            for (int k = 0; k < strArray.length; k++) {
                result += strArray[k];
                if (strArray.length != k + 1) {
                    result += delim;
                }
            }
            return result;
        } else {
        	return null;
        }
    }

    /**
     * 넘어온 문자열을 해당 구분자로 잘라 배열로 반환한다
	 * 예) String[] result = divideStringArray("A01,B01,C01", ",");
	 * return "A01,B01,C01"
     * @param    str
     * @param    delim
     * @return   분리된 문자열 배열
     */
    public static String[] divideStringArray(String str, String delim) {
        if (str == null) return null;
        String[] result = null;
        StringTokenizer st = new StringTokenizer(str, delim);
        result = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++)
            result[i] = st.nextToken();
        return result;
    }

    /**
	 * 넘어온 문자열을 length만큼씩 잘라 배열로 반환한다<br>
	 * 예) String[] result = divideStringLength("A01B01C01", 3);<br>
	 *     return {"A01","B01","C01"}
	 * @param str
	 * @param len
	 * @return 분리된 문자열 배열
	 */
	public static String[] divideStringLength(String str, int len){

		if( str == null ) return null;

		int array_count = str.length()/len;
		String[] result = new String[array_count];
		int start = 0;
		int end   = 0;
		for(int i=0; i<array_count; i++){
			start = i*len;
			end   = (i+1)*len;
			result[i] = str.substring(start,end);
		}
		return result;
	}

    /**
     * 문자열 배열을 싱클쿼트 구분자 형태의 문자로 변환 후 반환
	 * 예)<br> String[] array = {"aaa","bbb","ccc"}
     * String result = getSingleQuotSection(array)
     * return "'aaa','bbb','ccc'"
     * @param   array
     * @return  해당 문자열을 조합한 새로운 문자열
     */
    public static String getSingleQuotSection(String[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append("'").append(array[i]).append("'").append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * 해당문자가 몇개가 있는지 갯수 반환
     * @param   str
     * @param   searchStr
     * @return  검색 카운터
     */
    public static int getSearchStringCount(String str, String searchStr) {
    	StringTokenizer st = new StringTokenizer(str, searchStr);
    	return st.countTokens();
    }

    /**
	 * 한글값을 url 주소로 넘겨줄 수 있게 인코딩 함
	 * @param str
	 * @return 인코딩된 문자열
	 */
	public static String URLEncoder(String str) {
		String encodeStr = "";
		try {
			if(str==null) {
				str = "";
			}
			encodeStr = URLEncoder.encode(str, "UTF-8");
		} catch(UnsupportedEncodingException ex) {
			ex.printStackTrace(System.out);
			return null;
		}
		return encodeStr;
	}

	/**
	 * 한글값을 url 주소로 받을 수 있게 디코딩 함
	 * @param str
	 * @return 디코딩된 문자열
	 */
	public static String URLDecoder(String str) {
		String decodeStr = "";
		try {
			if(str==null) {
				str = "";
			}
			decodeStr = URLDecoder.decode(str, "UTF-8");
		} catch(UnsupportedEncodingException ex) {
			ex.printStackTrace(System.out);
			return null;
		}
		return decodeStr;
	}

	/**
	 * 8859_1을 KSC5601(EUC_KR)로 변환
	 * @param ascii - 8859_1로 조합된 문자열
	 * @return EUC_KR로 조합된 문자열
	 */
	public static String toEUC_KR(String ascii){
		if (ascii == null)
			return null;
		try{
			ascii = new String ( ascii.getBytes ("8859_1"), "EUC_KR" );
		}catch(Exception e){
			e.printStackTrace(System.out);
			return null;
		}
		return ascii;
	}

	/**
	 * KSC5601(EUC_KR)을 8859_1로 변환
	 * @param euc_kr - EUC_KR로 조합된 문자열
	 * @return 8859_1로 조합된 문자열
	 */
	public static String toEn(String euc_kr){
		if (euc_kr == null)
			return null;
		try{
			euc_kr = new String ( euc_kr.getBytes ("EUC_KR"), "8859_1" );
		}catch(Exception e){
			e.printStackTrace(System.out);
			return null;
		}
		return euc_kr;
	}

	/**
	 * 해당 문자가 null인지만 검사하여 맞으면 true 아니면 false반환
	 * @param str - 검사할 문자열
	 * @return 검사 결과 여부
	 */
	public static boolean isNull(String str){
		return (str == null) ? true : false;
	}

	/**
	 * 해당 객체가 null인지만 검사하여 맞으면 true 아니면 false반환
	 * @param obj - 검사할 문자열
	 * @return 검사 결과 여부
	 */
	public static boolean isNull(Object obj){
		return (obj == null) ? true : false;
	}

	/**
	 * 해당 문자가 null이거나 공백이면 true 아니면 false반환
	 * @param str - 검사할 문자열
	 * @return 검사 결과 여부
	 */
	public static boolean isEmptyString(String str){
		return (str == null || "".equals(str)) ? true : false;
	}

	/**
	 * 해당 객체가 null 이거나 empty 일경우 true를 반환
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		return (obj == null || "".equals(obj.toString())) ? true : false;
	}

	/**
	 * 해당 문자가 null이거나 공백이면 0을 그렇지 않으면 해당 문자의 int값을 반환한다
	 * @param str - 검사할 문자열
	 * @return 검사 결과 숫자
	 */
	public static int toInt(String str){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString) ? 0 : Integer.parseInt(str);
	}

	/**
	 * 해당 문자가 null이거나 공백이면 0을 그렇지 않으면 해당 문자의 char값을 반환한다
	 * @param str - 검사할 문자열
	 * @return 검사 결과 문자
	 */
	public static char toChar(String str){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString || str.length() > 1) ? 0 : str.charAt(0);
	}

	/**
	 * 해당 문자가 null이거나 공백이면 0값을 그렇지 않으면 해당 문자열 BigDecimal값을 반환
	 * @param str - 검사할 문자열
	 * @return 검사 결과 숫자
	 */
	public static BigDecimal toBigDecimal(String str){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString) ? new BigDecimal(0) : new BigDecimal(str);
	}

	/**
	 * 해당 문자가 null이거나 공백이면 대체 문자열을 반환한다
	 * @param str - 검사할 문자열
	 * @param newStr - 대체할 문자열
	 * @return 검사 결과 문자열
	 */
	public static String toBlank(String str, String newStr){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString) ? newStr : str;
	}

	/**
	 * 해당 문자가 null이거나 공백이면 대체할 문자열 숫자를 반환한다
	 * @param str - 검사할 문자열
	 * @param newStr - 대체할 문자열
	 * @return 검사 결과 숫자
	 */
	public static int toInt(String str, String newStr){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString) ? Integer.parseInt(newStr) : Integer.parseInt(str);
	}

	/**
	 * 해당 문자가 null이거나 공백이면 해당 문자의 char값을 반환한다
	 * @param str - 검사할 문자열
	 * @param newStr - 대체할 문자열
	 * @return 검사 결과 문자
	 */
	public static char toChar(String str, char newStr){
		boolean isEmptyString = (str == null || "".equals(str)) ? true : false;
		return (isEmptyString || str.length() > 1) ? newStr : str.charAt(0);
	}

	/**
	 * 문자의 구분자의 구분한 값중 마지막 문자
	 * @param s	- 문자열
	 * @param delim - 구분자
	 * @return
	 */
	public static String getLast(String s, String delim) {
		if (isNull(s))
			return "null";
		if (!contains(s, delim))
			return s;
		StringTokenizer st = new StringTokenizer(s, delim);
		String last  = null;
		while (st.hasMoreTokens()) {
			last = st.nextToken();
		}
		return last;
	}

	/**
	 *
	 * @param s
	 * @param in
	 * @return
	 */
	public static boolean contains(String s, String in) {
		if (isNull(s))
				return false;
		return s.indexOf(in) > -1 ? true : false;
	}

	/**
	 *
	 * @param s
	 * @param in
	 * @return
	 */
	public static boolean contains(String s, String[] in) {
		boolean flag = false;
		if (isNull(in))
			return false;
		for (int i=0; i<in.length; i++) {
			if (equals(s, in[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}

    public static boolean equals(String s, String s1) {
        if(s == null && s1 == null)
            return true;
        if(s == null || s1 == null)
            return false;
        else
            return s.equals(s1);
    }

    public static boolean equalsIgnoreCase(String s, String s1) {
        if(s == null && s1 == null)
            return true;
        if(s == null || s1 == null)
            return false;
        else
            return s.equalsIgnoreCase(s1);
    }

    public static boolean equals(int i, int j) {
        return i == j;
    }

	public static String toUpperCase(String s) {
		if (isNull(s))
			return s;
		return s.toUpperCase();
	}

	public static String toLowerCase(String s) {
		if (isNull(s))
			return s;
		return s.toLowerCase();
	}

    public static int getByte(String s)
    {
        int i = 1;
        if(s != null)
        {
            for(int j = 0; j < s.length(); j++)
            {
                char c = s.charAt(j);
                if(isHalf(c))
                    i++;
                else
                    i += 2;
            }

        }
        return i;
    }

    private static boolean isHalf(char c)
    {
        return ' ' <= c && c < '\177';
    }

    public static String cut(String s, int max)
	{
		String tBuff = null;
		byte[] abTmp = null;
		int    nIdx  = 0;

		if ( s == null ) {
			return "";
		}


		abTmp = s.getBytes();
		if ( abTmp.length > max ) {
			for( int i = 0; i< i; i++ ) {
				if ( abTmp[i] < 0 ) {
					nIdx++;
				}
			}

			if ( nIdx % 2 == 0 ) {
				tBuff = new String( abTmp, 0, max );
			} else {
				tBuff = new String( abTmp, 0, max - 1 ) + " ";
			}
		}
		else {
			tBuff = s;
		}
		return tBuff;
	}

    // Right Left a String with a specified character.
    public static String lpad(String s, int n , String replace )
    {
        return  StringUtils.leftPad(s, n, replace);
    }

    // Right pad a String with a specified character.
    public static String rpad(String s, int n , String replace )
    {
        return  StringUtils.rightPad(s, n, replace);
    }

    // +1 => Left pad a String with a specified character.
    public static String increaseLpad(String s, int n , String replace )
    {
        long tempValue = Long.parseLong(s);
        tempValue = tempValue + 1;
        s = Long.toString(tempValue);
        return  lpad(s, n, replace);
    }

    /**
     * 시티아이디를 검사하여 빈문자 일경우 default 로 반환
     * @param citySeq
     * @return citySeq
     */
    public static long nvlCitySeq(String citySeq) {
    	long lCitySeq = 0;
    	if(citySeq==null) {
    	} else {
    		lCitySeq = new Long(citySeq).longValue();
    	}
    	return lCitySeq;
    }

    /**
     * 등록자 아이디 검사하여 빈문자 일경우 system 으로 반환
     * @param registId
     * @return registId
     */
    public static String nvlRegistId(String registId) {
    	if(registId==null || registId.equals("")) {
    		registId = "admin";
    	}
    	return registId;
    }

    /**
     * 등록자 이름 검사하여 빈문자 일경우 시스템 으로 반환
     * @param registName
     * @return registName
     */
    public static String nvlRegistName(String registName) {
    	if(registName==null || registName.equals("")) {
    		registName = "관리자";
    	}
    	return registName;
    }

    /**
     * 등록자 아이피 검사하여 빈문자 일경우 127.0.0.1 으로 반환
     * @param nvlRegistIp
     * @return nvlRegistIp
     */
    public static String nvlRegistIp(String nvlRegistIp) {
    	if(nvlRegistIp==null || nvlRegistIp.equals("")) {
    		nvlRegistIp = "127.0.0.1";
    	}
    	return nvlRegistIp;
    }

    /**
     * @MethodName : getSessionCityId
     * @작성일	   : 2010. 4. 15.
     * @작성자	   : 노상용
     * @변경이력   :
     * @Method설명 : 세션 시티 아이디 반환
     * @param request
     * @return
     */
    public static long getSessionCityId(HttpServletRequest request) {
    	HttpSession httpSession = request.getSession();
		return nvlCitySeq((String) httpSession.getAttribute("citySeq"));
    }

    /**
     * @MethodName : setCitySession
     * @작성일	   : 2010. 4. 15.
     * @작성자	   : 노상용
     * @변경이력   :
     * @Method설명 : 오브젝트에 시티인덱스와 로그 데이타를 지정한다
     * @param request
     * @param target
     */
    public static void setCitySession(HttpServletRequest request, Object target) {

    	long cityId = getSessionCityId(request);

    	setCityIdInfo(cityId, target);

    	setSessionLogInfo(request, target);
    }

    /**
     * @MethodName : setCityIdInfo
     * @작성일	   : 2010. 4. 15.
     * @작성자	   : 노상용
     * @변경이력   :
     * @Method설명 : 오브젝트에 시티인덱스를 지정한다
     * @param cityId
     * @param target
     */
    @SuppressWarnings("unchecked")
	public static void setCityIdInfo(long cityId, Object target) {
    	if(target instanceof HashMap<?, ?>) {
    		((HashMap)target).put("citySeq", cityId);
    	} else {
    		Class clazz = target.getClass();
    		Field[] field = clazz.getDeclaredFields();
    		String fieldName = null;
    		String tmpString;

    		for(int i = 0; i < field.length; i++) {
    			fieldName = field[i].getName();
    			String fieldType = field[i].getType().getName();

    			if(fieldName.equals("citySeq")) {
    				tmpString = new String(String.valueOf(cityId));
    				field[i].setAccessible(true);

    				if(fieldType.equals("long")) {
    					try {
							field[i].setLong(target, Long.parseLong(tmpString));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    				} else if(fieldType.equals("java.lang.Long")) {
    					try {
							field[i].set(target, Long.valueOf(tmpString));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    				}

    			}
    		}
    	}
    }

    /**
     * @MethodName : setSessionLogInfo
     * @작성일	   : 2010. 4. 15.
     * @작성자	   : 노상용
     * @변경이력   :
     * @Method설명 : 오브젝트에 로그 데이타를 지정한다
     * @param request
     * @param target
     */
    @SuppressWarnings("unchecked")
	public static void setSessionLogInfo(HttpServletRequest request, Object target) {

    	HttpSession httpSession = request.getSession();

    	String registId = nvlRegistId((String) httpSession.getAttribute("registId"));
    	String registName = nvlRegistName((String) httpSession.getAttribute("registName"));
    	String registIp = request.getRemoteAddr();

    	if(target instanceof HashMap<?, ?>) {
    		((HashMap)target).put("registId", registId);
    		((HashMap)target).put("registName", registName);
    		((HashMap)target).put("registIp", registIp);
    	} else {
    		Class clazz = target.getClass();
    		Field[] field = clazz.getDeclaredFields();
    		String fieldName = null;
    		String tmpString;

    		for(int i = 0; i < field.length; i++) {
    			fieldName = field[i].getName();

    			if(fieldName.equals("registId")) {
    				tmpString = new String(registId);
    				field[i].setAccessible(true);
    				try {
						field[i].set(target, tmpString);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}

    			if(fieldName.equals("registName")) {
    				tmpString = new String(registName);
    				field[i].setAccessible(true);
    				try {
						field[i].set(target, tmpString);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}

    			if(fieldName.equals("registIp")) {
    				tmpString = new String(registIp);
    				field[i].setAccessible(true);
    				try {
						field[i].set(target, tmpString);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}
    	}
    }

    /**
	 * 파일이름만 알아냄
	 * @param fileName
	 * @return fileName(String)
	 */
	@SuppressWarnings("unused")
	public static String getFileName(String fileName) {
		int idx = fileName.lastIndexOf("\\");
		if(idx == -1) {
			idx = fileName.lastIndexOf("/");
		}

		String filePath_ = "";
		String fileName_ = "";

		if(idx == -1) {
			//ie8
			fileName_ = fileName;
		} else {
			//other
			filePath_ = fileName.substring(0, idx);
			fileName_ = fileName.substring(idx + 1);
		}

		return fileName_;
	}

	/**
     * @MethodName : HtmlTagClean
     * @작성일	   : 2010. 4. 16.
     * @작성자	   : 노상용
     * @변경이력   :
     * @Method설명 : html 태그 제거
     * @param s
     * @return String
     */
    public static String HtmlTagClean(String s) {
    	if (s == null) {
    		return null;
    	}

    	Matcher m;

    	m = Patterns.SCRIPTS.matcher(s);
    	s = m.replaceAll("");
    	m = Patterns.STYLE.matcher(s);
    	s = m.replaceAll("");
    	m = Patterns.TAGS.matcher(s);
    	s = m.replaceAll("");
    	m = Patterns.ENTITY_REFS.matcher(s);
    	s = m.replaceAll("");
    	m = Patterns.WHITESPACE.matcher(s);
    	s = m.replaceAll(" ");

    	return s;
    }

    /**
     * @FileName	 : StringUtil.java
     * @Project      : cla-madame-core
     * @Date         : 2010. 4. 16.
     * @작성자       : 노상용
     * @변경이력	 :
     * @프로그램설명 : HTML 제거 변경 패턴
     */
    private interface Patterns {
    	// javascript tags and everything in between
    	public static final Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);

    	public static final Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL);
    	// HTML/XML tags

    	public static final Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");

		@SuppressWarnings("unused")
		public static final Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
    	// entity references
    	public static final Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");
    	// repeated whitespace
    	public static final Pattern WHITESPACE = Pattern.compile("\\s\\s+");
    }

    /**
     * @MethodName : cutString
     * @작성일	   : 2010. 4. 16.
     * @작성자	   : 노상용
     * @변경이력   :
     * @Method설명 : 바이트로 글자수 자르기
     * @param s
     * @param len
     * @param tail
     * @return String
     */
    public static String cutString(String s, int len, String tail) {

    	if (s == null || s.equals("")) {
            return "";
    	}

    	String rs = "";

    	try {

	        int srcLen = realLength(s);

	        ////System.out.println("input len : " + len);
	        ////System.out.println("string len : " + srcLen);

	        if (srcLen < len)
	            return s;

	        String tmpTail = tail;
	        if (tail == null)
	            tmpTail = "";

	        int tailLen = realLength(tmpTail);
	        if (tailLen > len)
	            return "";

	        char a;
	        int i = 0;
	        int realLen = 0;
	        for (i = 0; i < len - tailLen && realLen < len - tailLen; i++) {
	           a = s.charAt(i);
	           if ((a & 0xFF00) == 0)
	               realLen += 1;
	           else
	               realLen += 3;
	        }

	        while (realLength(s.substring(0, i)) > len - tailLen) {
	            i--;
	        }

	        rs = s.substring(0, i) + tmpTail;

    	} catch (Exception e) {
			e.printStackTrace(System.out);
		}

    	return rs;
    }

    /**
     * @MethodName : cutString
     * @작성일	   : 2011. 6. 2.
     * @작성자	   : 서동훈
     * @변경이력   :
     * @Method설명 : 바이트로 글자수 자르기(한글은 2로침)
     * @param s
     * @param len
     * @param tail
     * @return String
     */
    public static String cutString2(String s, int len, String tail) {

    	if (s == null || s.equals("")) {
    		return "";
    	}

    	String rs = "";

    	try {

    		int srcLen = realLength(s);

    		////System.out.println("input len : " + len);
    		////System.out.println("string len : " + srcLen);

    		if (srcLen < len)
    			return s;

    		String tmpTail = tail;
    		if (tail == null)
    			tmpTail = "";

    		int tailLen = realLength(tmpTail);
    		if (tailLen > len)
    			return "";

    		char a;
    		int i = 0;
    		int realLen = 0;
    		for (i = 0; i < len - tailLen && realLen < len - tailLen; i++) {
    			a = s.charAt(i);
    			if ((a & 0xFF00) == 0)
    				realLen += 1;
    			else
    				realLen += 2;
    		}

    		while (realLength(s.substring(0, i)) > len - tailLen) {
    			i--;
    		}

    		rs = s.substring(0, i) + tmpTail;

    	} catch (Exception e) {
    		e.printStackTrace(System.out);
    	}

    	return rs;
    }

    /**
     * @MethodName : realLength
     * @작성일	   : 2010. 4. 16.
     * @작성자	   : 노상용
     * @변경이력   :
     * @Method설명 : string 의 바이트를 구한다
     * @param s
     * @return int
     * @throws UnsupportedEncodingException
     */
    public static int realLength(String s) throws UnsupportedEncodingException {
        return s.getBytes("utf-8").length;
    }

    @SuppressWarnings("unused")
	public static void makeWeddingThumbnail(String serviceCode, String imageFileUrl, String sourceId) {

    	StringBuffer sb = new StringBuffer("");
		sb.append("<list>");
		sb.append("<image>");
		sb.append("<serviceCode>"+serviceCode+"</serviceCode>");
		sb.append("<sourceId>"+sourceId+"</sourceId>");
		sb.append("<version>1</version>");

		//sb.append("<url>http://203.235.206.45/travel/upload/"+photoInfo.getPhotoName()+"</url>");
		sb.append("<url>"+imageFileUrl+"</url>");

		sb.append("<tag>weddingImg</tag>");
		sb.append("</image>");
		sb.append("</list>");

		String sbStr = sb.toString();

////System.out.println("Grim request = "+sbStr);

		try {
			URL url = new URL("http://203.235.206.55:8080/grim-crawler/crawl");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setRequestProperty("Content-Type","application/xml; charset=utf-8");
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			OutputStream out_stream = con.getOutputStream();

			out_stream.write( sbStr.getBytes("UTF-8") );
		    out_stream.flush();
		    out_stream.close();

		    InputStream is      	= null;
		    BufferedReader in	= null;
		    String data         	= "";

		    is  = con.getInputStream();
		    in  = new BufferedReader(new InputStreamReader(is), 8 * 1024);

		    String line = null;
		    StringBuffer buff   = new StringBuffer();

		    int resCode = 0; // RMS 와의 연결 응답값
		    resCode = con.getResponseCode();

		    while ( ( line = in.readLine() ) != null ) {
		        buff.append(line + "\n");
		    }
		    data    = buff.toString().trim();
////System.out.println("Grim result = "+resCode);


		    Thread.sleep(100);

		 } catch (IOException e) {
			 //System.out.println("IOError==="+e);
			 e.printStackTrace();
		 } catch(Exception e) {
			 //System.out.println("EXError==="+e);
			 e.printStackTrace();
		 }
    }

	/**
	 * Object가 null 일경우 기본문자열을 리턴한다.
	 *
	 * @param obj
	 *            Object
	 * @param def
	 *            기본 문자열
	 * @return String
	 * @sample StringUtil.convNullObj(obj, "");
	 */
	public static String convNullObj(Object obj, String def) {
            if(def == null){
                def = "";
            }
            if (obj==null || "".equals(obj)){
                    return def;
            }
            return obj.toString().trim();
	}

    /**
     * Object가 null 일경우 ""을 리턴한다.
     *
     * @param obj
     *            Object
     * @return String obj가 변환된 String. null 일경우는 "".
     * @sample StringUtil.convNullObj(obj);
     */
    public static String convNullObj(Object obj) {
        if (obj==null){
            return "";
        }
        return obj.toString();
    }


    /**
     * @Method Name	: telNoFormat
     * @작성일	    : 2011. 1. 27.
     * @작성자	    : nohjh
     * @Method 설명	: 전화번호에 구분자(-)를 넣어 표현한다.
     * @param String telNo
     * @return String obj가 변환된 String. null 일경우는 "".
     */
    public static String telNoFormat(String param){
    	String telNo = "";
    	if(param==null){
    		telNo = "";
    	}else if(param.length()<9 || param.length()>11){
    		telNo = param;
    	}else if(param.length()==9){					// 02-123-1234
			telNo = param.substring(0, 2)+"-"+param.substring(2, 5)+"-"+param.substring(5);
    	}else if(param.length()==10){
    		if(param.startsWith("02")){				// 02-1234-1234
    			telNo = param.substring(0, 2)+"-"+param.substring(2, 6)+"-"+param.substring(6);
    		}else{												// 011-123-1234
    			telNo = param.substring(0, 3)+"-"+param.substring(3, 6)+"-"+param.substring(6);
    		}
    	}else if(param.length()==11){				// 032-1234-1234, 010-1234-1234
    		telNo = param.substring(0, 3)+"-"+param.substring(3, 7)+"-"+param.substring(7);
		}
    	return telNo;
    }

    /**
     * @Method Name	: containsHangul
     * @작성일	    : 2011. 2. 12.
     * @작성자	    : DOLLY217
     * @Method 설명	: 스트링에 포함된 한글을 substring으로 자르기 위해 처리하는 함수
     * @param str
     * @return
     */
    public static String containsHangul(String str) {
		String returnValue = "";
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
			if (Character.UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock)
					|| Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
							.equals(unicodeBlock)
					|| Character.UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock))
				returnValue = (new StringBuilder()).append(returnValue).append(
						ch).append(" ").toString();
			else
				returnValue = (new StringBuilder()).append(returnValue).append(
						ch).toString();
		}

		return returnValue;
	}

    /**
     * @Method Name	: maskEmail
     * @작성일	    : 2011. 2. 12.
     * @작성자	    : DOLLY217
     * @Method 설명	: 이메일 주소의 일부를 마스크 처리한다. dolly2**@famz.co.kr
     * @param str
     * @return
     */
    public static String maskEmail(String str) {
		String returnValue = "";

		StringTokenizer token = new StringTokenizer(str, "@");
		if(token != null){
			String email1= 	token.nextToken();
			String email2 = token.nextToken();
			returnValue = email1.substring(0,email1.length()-2)+"**@"+email2;
		}

		return returnValue;
	}

    /**
     * @Method Name	: maskPhoneNumber
     * @작성일	    : 2011. 2. 12.
     * @작성자	    : DOLLY217
     * @Method 설명	: 전화번호의 일부를 마스크 처리한다. 010-****-2172
     * @param str
     * @return
     */
    public static String maskPhoneNumber(String str) {
		String returnValue = "";

		StringTokenizer token = new StringTokenizer(str, "-");
		if(token != null){
			String number1= 	token.nextToken();
			String number2 = token.nextToken();
			String number3 = token.nextToken();
			String mask = "";
			for(int i = 0 ; i < number2.length() ; i++){
				mask += "*";
			}
			returnValue = number1+"-"+mask+"-"+number3;
		}

		return returnValue;
	}

    public static String setPhoneNumber(String str) {
    	String returnValue = "";
    	if(str  == null) {
    		returnValue = "";
    	}
    	if(str.length() == 8) {
    		returnValue = str.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
    	} else if (str.length() == 12) {
    		returnValue = str.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
    	} else {
    		returnValue = str.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    	}
    	return returnValue;
    }
    /**
     * 스트링 자르기
     * 지정한 정수의 개수 만큼 빈칸(" ")을 스트링을 구한다.
     * 절단된 String의 바이트 수가 자를 바이트 개수를 넘지 않도록 한다.
     *
     * @param str 원본 String
     * @param int 자를 바이트 개수
     * @param char + or -
     * @return String 절단된 String
     */
     public static String cutStr(String str, int length ,char type) {
    	 byte[] bytes = str.getBytes();
    	 int len = bytes.length;
    	 int counter = 0;

    	 if (length >= len) {
		 	StringBuffer sb = new StringBuffer();
		 	sb.append(str);
		 	for(int i=0;i<length-len;i++){
	 			sb.append(' ');
		 	}
		 	return sb.toString();
    	 }

    	 for (int i = length - 1; i >= 0; i--) {
    		 if (((int)bytes[i] & 0x80) != 0)
    			 counter++;
    	 }

    	 String f_str = null;

    	 if(type == '+'){
    		 f_str = new String(bytes, 0, length + (counter % 3));
    	 }else if(type == '-'){
    		 f_str = new String(bytes, 0, length - (counter % 3));
    	 }else{
    		 f_str = new String(bytes, 0, length - (counter % 3));
    	 }

    	 return f_str;
     }

     @SuppressWarnings("unused")
	public static String encodeReturnUrl(String s) {

 		byte byte0 = 10;

 		byte[] bString = s.getBytes();

 		StringBuffer stringbuffer = new StringBuffer(bString.length);

 		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(
 				byte0);

 		OutputStreamWriter outputstreamwriter = new OutputStreamWriter(
 				bytearrayoutputstream);

 		int mValue = 0;

 		for (int i = 0; i < bString.length; i++)

 		{

 			char c = (char) bString[i];

 			mValue = (bString[i] < 0) ? bString[i] + 256 : bString[i];

 			// if(mValue > 127){ //2byte

 			// stringbuffer.append(Integer.toHexString(c));

 			// }

 			// else{

 			if (dontNeedEncoding.get(c))

 			{

 				stringbuffer.append(Integer.toHexString(c));

 				continue;

 			}

 			try

 			{

 				outputstreamwriter.write(c);

 				outputstreamwriter.flush();

 			}

 			catch (IOException _ex)

 			{

 				bytearrayoutputstream.reset();

 				continue;

 			}

 			byte abyte0[] = bytearrayoutputstream.toByteArray();

 			for (int j = 0; j < abyte0.length; j++)

 			{

 				char c1 = Character.forDigit(abyte0[j] >> 4 & 0xf, 16);

 				if (Character.isLetter(c1))

 					c1 -= ' ';

 				stringbuffer.append(c1);

 				c1 = Character.forDigit(abyte0[j] & 0xf, 16);

 				if (Character.isLetter(c1))

 					c1 -= ' ';

 				stringbuffer.append(c1);

 			}

 			bytearrayoutputstream.reset();

 			// }

 		}

 		return stringbuffer.toString();

 	}

 	static BitSet dontNeedEncoding;

 	static final int caseDiff = 32;

 	static

 	{

 		dontNeedEncoding = new BitSet(256);

 		for (int i = 97; i <= 122; i++)

 			dontNeedEncoding.set(i);

 		for (int j = 65; j <= 90; j++)

 			dontNeedEncoding.set(j);

 		for (int k = 48; k <= 57; k++)

 			dontNeedEncoding.set(k);

 		dontNeedEncoding.set(32);

 		dontNeedEncoding.set(45);

 		dontNeedEncoding.set(95);

 		dontNeedEncoding.set(46);

 		dontNeedEncoding.set(42);

 	}

 	public static String decodeReturnUrl(String cEncodeString) {

 		StringBuffer stringbuffer = new StringBuffer(cEncodeString.length() / 2);

 		for (int i = 0; i < cEncodeString.length(); i = i + 2) {

 			stringbuffer.append((char) Integer.parseInt(cEncodeString
 					.substring(i, i + 2), 16));

 		}

 		return stringbuffer.toString();

 	}

 	
 	/**
 	 *
 	 * @Method Name : shNumLngth
 	 * @작성일 : 2019. 1. 20.
 	 * @작성자 : s1212921
 	 * @변경이력 : 
 	 * @Method 설명 : 주민번호 이외의 사업자번호등이 앞자리에 000 문자열을 붙이는 형태여서 필요 
 	 * @param shNum
 	 * @return
 	 *
 	 */
 	public static String shNumLngth(String shNum) {
 		if(CommonUtil.isEmpty(shNum)){
 			return nvl(shNum, "");
 		}else{
 			int bb = 13 - shNum.length();
 			if(shNum.length() < 13) {
 				for(int i=0 ; i < bb ; i++) {
 					shNum = "0" + shNum;
 				}
 			}
 		}
 		return shNum;
 	}
 	
 	
    /**
     *
     * @Method Name : cleanXSS
     * @작성일 : 2019. 1. 29.
     * @작성자 : s1212921
     * @변경이력 : 
     * @Method 설명 : XSS 보안관련 코드 삭제  
     * @param value
     * @return
     *
     */
    public static String cleanXSS(String value) {
        //You'll need to remove the spaces from the html entities below
    	value = nvl(value);
		value = value.replace("<", "&lt;").replace(">", "&gt;");
		value = value.replace("\\(", "&#40;").replace("\\)", "&#41;");
		value = value.replace("'", "&#39;");
		value = value.replace("eval\\((.*)\\)", "");
		value = value.replace("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replace("script", "");
	return value;
    }

    public static boolean juminCheck(String value){
    	
    	String regExp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])[1-4][0-9]{6}$";
    	
    	if(CommonUtil.isEmpty(value)){
    		return false;
    	}else{
    		// 주민번호형식이면 true 리턴
    		return value.matches(regExp);
    	}
    }
    
    /**
     * 데이타 스트링 포맷으로 변경
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Object date, String pattern){
    	
    	try {
    		if(CommonUtil.isEmpty(date)){
    			return "";
    		}else{
    			if(CommonUtil.isEmpty(pattern)) {
    				pattern = "yyyy-MM-dd";
    			}
    			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    			String result = "";
    			result = formatter.format(date);
    			return result;
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
			return "";
		}
    		
    }
    
    
    
}

