package com.neoforth.sample.socket;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint("/sample/socket/broadcasting")
public class broadcasting {

//	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

//	@OnMessage
//	public void onMessage(String message, Session session) throws IOException {
//		System.out.println(message);
//		synchronized (clients) {
//			// Iterate over the connected sessions
//			// and broadcast the received message
//			for (Session client : clients) {
//				
//				System.out.println("client:"+client);
//				System.out.println("session:"+session);
//				
//				if (!client.equals(session)) {
//					client.getBasicRemote().sendText(message);
//				}
//			}
//		}
//	}
//
//	@OnOpen
//	public void onOpen(Session session) {
//		// Add session to the connected sessions set
//		System.out.println(session);
//		clients.add(session);
//	}
//
//	@OnClose
//	public void onClose(Session session) {
//		// Remove session from the connected sessions set
//		clients.remove(session);
//	}
	
	
	//유저 집합 리스트
		static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());

		/**
		 * 웹 소켓이 접속되면 유저리스트에 세션을 넣는다.
		 * 
		 * @param userSession 웹 소켓 세션
		 */
		@OnOpen
		public void handleOpen(Session userSession) {
			
			// user id 를 넣어줄꺼
			// userSession.getUserProperties().put("username", message);
			
			sessionUsers.add(userSession);
		}

		/**
		 * 웹 소켓으로부터 메시지가 오면 호출한다.
		 * 
		 * @param message     메시지
		 * @param userSession
		 * @throws IOException
		 */
		@OnMessage
		public void handleMessage(String message, Session userSession) throws IOException {
			String username = (String) userSession.getUserProperties().get("username");
			//세션 프로퍼티에 username이 없으면 username을 선언하고 해당 세션을으로 메시지를 보낸다.(json 형식이다.)
			//최초 메시지는 username설정
			if (username == null) {
				userSession.getUserProperties().put("username", message);
				userSession.getBasicRemote().sendText(buildJsonData("System", "you are now connected as " + message));
				return;
			}
			//username이 있으면 전체에게 메시지를 보낸다.
			Iterator<Session> iterator = sessionUsers.iterator();
			while (iterator.hasNext()) {
				if(username.equals((String) iterator.next().getUserProperties().get("username"))) {
				
					System.out.println("username:"+username);
					userSession.getBasicRemote().sendText(buildJsonData(username, "you are now connected as " + message));
				}
				
			}
			
		}

		/**
		 * 웹소켓을 닫으면 해당 유저를 유저리스트에서 뺀다.
		 * 
		 * @param userSession
		 */
		@OnClose
		public void handleClose(Session userSession) {
			sessionUsers.remove(userSession);
		}

		/**
		 * json타입의 메시지 만들기
		 * 
		 * @param username
		 * @param message
		 * @return
		 */
		public String buildJsonData(String username, String message) {
			JsonObject jsonObject = Json.createObjectBuilder().add("message", username + " : " + message).build();
			StringWriter stringwriter = new StringWriter();
			try (JsonWriter jsonWriter = Json.createWriter(stringwriter)) {
				jsonWriter.write(jsonObject);
			}
			return stringwriter.toString();
		}
		
		
		@Scheduled(cron="1 * * * * *")
		public void taskSchedulerTest() {
			System.out.println("SCHEDULE GOGOGOGOOGGOGOGO");
			Session userSession;
			try {
				
				// 특정유저에세 메세지이벤트 발
				if(sessionUsers.size() > 0) {
					
					System.out.println("SCHEDULE REAL:"+sessionUsers);
					
					// 특정 아이디의 경우 대상에게 신호보
					for(int i=0; i<sessionUsers.size(); i++) {
						userSession = sessionUsers.get(i);
						if("123".equalsIgnoreCase((String) userSession.getUserProperties().get("username"))){
							String message = String.valueOf(Math.random());
							handleMessage(message, userSession);
							break;
						}
					}
					
				}

			} catch (Exception ex) {
			    ex.printStackTrace();
			}		
		}
		
		
	}