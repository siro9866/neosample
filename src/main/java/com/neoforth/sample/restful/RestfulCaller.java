package com.neoforth.sample.restful;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neoforth.sample.utils.CommonUtil;

import net.sf.json.JSONObject;


public class RestfulCaller {
	
	private static final Logger logger = LoggerFactory.getLogger(RestfulCaller.class);
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		URL url = null;
		String str = null;
		String strSum = "";
		
		try {// URL은 프로토콜(http)부터 다 써줘야 한다
//			url = new URL("http://localhost:8080/sample/restful/restfulJson.neo");
//			url = new URL("http://localhost:4200/homegraph?file=imageFile.png&agentUserId=leekw@neoforth.com");
			url = new URL("http://localhost:4200/homegraph");
		} catch (MalformedURLException e) {

			logger.debug("잘못된URL 입니다");
			e.printStackTrace();
		}// URL 객체만 생성하고 통신하고 있는 상태는 아님

		// 데이터긁어오기 , 읽을때 파일명이아닌 네트웍 주소를 가져와야함
		try {
			HttpURLConnection br_connection = (HttpURLConnection) url.openConnection();
			br_connection.setReadTimeout(3000);
			BufferedReader newbr = new BufferedReader(new InputStreamReader(br_connection.getInputStream(), "UTF-8"));

			while ((str = newbr.readLine()) != null) {
				strSum = strSum + str + "\n";
			}
			logger.debug("strSum:"+strSum);
			newbr.close();
			json.put("result", strSum);
			CommonUtil.getReturnCodeSuc(json);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("================================================================================================");
		System.out.println(json.toString());
		System.out.println("================================================================================================");
		
	}

}
