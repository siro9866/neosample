package com.neoforth.sample.socket;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioEchoClient implements Runnable {

	private final String hostAddress;
	private final int port;
	private final Selector selector;
	private static final byte LF=0x0A;
	private static final byte CR=0x0D;
	private String line = null;
	private static int PORT_NUMBER = 6000;
	private static final long TIME_OUT = 3000;
	private final ByteBuffer readBuffer = ByteBuffer.allocate(8192);
	
	public NioEchoClient(String hostAddress, int port) throws IOException{
		this.hostAddress = hostAddress;
		this.port = port;
		this.selector = Selector.open();
	}
	
	public SocketChannel createSocketChannel() throws IOException{
		SocketChannel client = SocketChannel.open(new InetSocketAddress(hostAddress, port));
		client.configureBlocking(false);
		return client;
	}
	
	@Override
	public void run(){
		SocketChannel client = null;
		try{
			client = createSocketChannel();
			
			if(!client.isConnected())
				client.finishConnect();
			
			System.out.println("서버에 전송할 자료를 입력하세요.");
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			while((line=keyboard.readLine())!=null){
				if(line.equals("quit")) break;
				
				client.register(selector,  SelectionKey.OP_WRITE);
				
				while(selector.select(TIME_OUT)!=0){
					Iterator<SelectionKey> selectedKeys =
							selector.selectedKeys().iterator();
					while(selectedKeys.hasNext()){
						SelectionKey key = selectedKeys.next();
						selectedKeys.remove();
						
						if(!key.isValid()) continue;
						if(key.isReadable())
							readData(key);
						else if(key.isWritable())
							writeData(key);
					}
				}
			}
		}catch(EOFException e){
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				client.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		System.out.println("client exits");
	}
	
	private void writeData(SelectionKey key) throws IOException{
		SocketChannel client = (SocketChannel) key.channel();
		readBuffer.clear();
		readBuffer.put(line.getBytes());
		readBuffer.put(LF);   
		//원칙은 CR도 추가해야 하나 유닉스서버의 경우 LF만으로도 허용
		
		//데이터를 버퍼에 입력했다면 다시 읽어 전송시킬 준비를 한다.
		readBuffer.flip();
		client.write(readBuffer);
		
		//서버에서 전송받을 자료를 기다리기 위해 소켓채널에 OP_READ를 등록한다.
		client.keyFor(selector).interestOps(SelectionKey.OP_READ);
	}
	
	private void readData(SelectionKey key) throws IOException{
		SocketChannel client = (SocketChannel) key.channel();
		//서버로부터 데이터를 받기 위해 버퍼를 비운다.
		readBuffer.clear();
		
		int numRead, totalRead=0;
		while((numRead=client.read(this.readBuffer))>0){
			totalRead += numRead;
		}
		
		//read() 메서드가 -1을 반환하면 서버로부터 FIN 패킷을 받은 것을 의미한다.
		if(numRead<0){
			client.close();
			key.cancel();
			throw new EOFException();
		}
		
		readBuffer.flip();
		byte[] receivedData = new byte[totalRead];
		//버퍼를 allocate로 할당했기에 arraycopy 메서드 사용이 가능하다.
		System.arraycopy(readBuffer.array(), 0, receivedData, 0,totalRead);
		System.out.println("Client read " + new String(receivedData).trim());
	}
	
	
	public static void main(String[] args) {
//		String host = args[0];
		String host = "localhost";
		try{
			NioEchoClient client = new NioEchoClient(host,PORT_NUMBER);
			Thread t = new Thread(client);
			t.start();
			t.join();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}