package com.neoforth.sample.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.neoforth.sample.mysql.vo.MysqlVO;
import com.neoforth.sample.utils.CommonUtil;

import net.sf.json.JSONObject;

@RestController("sample.socket.socketController")
public class SocketController {

	private static final Logger logger = LoggerFactory.getLogger(SocketController.class);

	private final int port = 9999;
	
	@RequestMapping(value = "/sample/socket/socketList", method = RequestMethod.GET)
	public ModelAndView socketList(@ModelAttribute MysqlVO paramVO, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		mav.setViewName("sample/socket/socketList");

		return mav;
	}
	
	@RequestMapping(value = "/sample/socket/broadsocket", method = RequestMethod.GET)
	public ModelAndView broadsocket(@ModelAttribute MysqlVO paramVO, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("sample/socket/broadsocket");
		
		return mav;
	}

	
	@RequestMapping(value = "/sample/socket/socketOpen")
	public JSONObject socketOpen(@ModelAttribute MysqlVO paramVO, HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		JSONObject json = new JSONObject();
		Map<String, String> resultMap = new HashMap<String, String>();
		
		String out = "";
		// 자동 close
		try (ServerSocket server = new ServerSocket()) {
			// 서버 초기화
			InetSocketAddress ipep = new InetSocketAddress(port);
			server.bind(ipep);

			System.out.println("Initialize complate");

			// LISTEN 대기
			Socket client = server.accept();
			System.out.println("Connection");

			// send,reciever 스트림 받아오기
			// 자동 close
			try (OutputStream sender = client.getOutputStream(); InputStream reciever = client.getInputStream();) {
				// 클라이언트로 hello world 메시지 보내기
				// 11byte 데이터
				String message = paramVO.getSearchValue();
				byte[] data = message.getBytes();
				sender.write(data, 0, data.length);

				// 클라이언트로부터 메시지 받기
				// 2byte 데이터
				data = new byte[2];
				reciever.read(data, 0, data.length);

				// 수신 메시지 출력
				message = new String(data);
				out = String.format("recieve - %s", message);
				System.out.println(out);
			}
			CommonUtil.getReturnCodeSuc(json);
			
			resultMap.put("sample_name", out);
			resultMap.put("searchValue", paramVO.getSearchValue());
			json.put("result", resultMap);
			
		} catch (Throwable e) {
			e.printStackTrace();
			CommonUtil.getReturnCodeFail(json, e.toString());
		}

		logger.info("서버:"+json.toString());
		
		return json;
	}

	@RequestMapping(value = "/sample/socket/socketClient")
	public JSONObject socketClient(@ModelAttribute MysqlVO paramVO, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		JSONObject json = new JSONObject();
		Map<String, String> resultMap = new HashMap<String, String>();
		CommonUtil.getReturnCodeFail(json);

		
		// 자동 close
		try (Socket client = new Socket()) {
			// 클라이언트 초기화
			InetSocketAddress ipep = new InetSocketAddress("127.0.0.1", port);
			// 접속
			client.connect(ipep);

			// send,reciever 스트림 받아오기
			// 자동 close
			try (OutputStream sender = client.getOutputStream(); InputStream receiver = client.getInputStream();) {
				// 서버로부터 데이터 받기
				// 11byte
				byte[] data = new byte[11];
				receiver.read(data, 0, 11);

				// 수신메시지 출력
				String message = new String(data);
				String out = String.format("recieve - %s", message);
				System.out.println(out);

				// 서버로 데이터 보내기
				// 2byte
				message = "OK";
				data = message.getBytes();
				sender.write(data, 0, data.length);
				
				resultMap.put("sample_name", out);
				resultMap.put("searchValue", paramVO.getSearchValue());
				json.put("result", resultMap);
				
			}
			CommonUtil.getReturnCodeSuc(json);
		} catch (Throwable e) {
			e.printStackTrace();
			CommonUtil.getReturnCodeFail(json, e.toString());
		}
		
		logger.info("클라이언트:"+json.toString());
		
		return json;
	}


}
