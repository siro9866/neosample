package com.neoforth.sample.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioEchoServer extends Thread {

	private ServerSocketChannel serverChannel;
	private Selector selector;
	public static int PORT_NUMBER = 6000;
	private static final long TIME_OUT = 3000;
	
	//allocateDirect가 더 빠르지만 자바 객체와 호환을 위해 allocate를 더 선호한다.
	private final ByteBuffer buffer = ByteBuffer.allocate(8192);
	private Boolean loop;
	
	public NioEchoServer(int port){
		SocketAddress isa = new InetSocketAddress(port);
		try{
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			
			ServerSocket s = serverChannel.socket();
			s.bind(isa);
			System.out.println("ISA:"+isa.toString());
			selector = Selector.open();
			
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			loop = true;
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		try{
			while(loop){
				int n = selector.select(TIME_OUT);
				if(n==0) continue; //타임 아웃의 반환값은 0
				
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> it = keys.iterator();
				while(it.hasNext()){
					SelectionKey key = it.next();
					System.out.println("SERVER(key):"+key);
					if(!key.isValid()) continue;
					if(key.isAcceptable()){
						acceptData(key);
					}else if(key.isReadable()){
						processData(key);
					}
					it.remove();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void acceptData(SelectionKey key) throws Exception{
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		SocketChannel channel = server.accept();
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		
		channel.register(selector, SelectionKey.OP_READ);
	}
	
	protected void processData(SelectionKey key) throws Exception{
		if(key == null)
			return;
		try{
			SocketChannel channel = (SocketChannel) key.channel();
			
			buffer.clear();
			int count = channel.read(buffer);

			//클라이언트가 접속을 끊기 위해 소켓 채널을 닫았을 때 read는 -1 반환
			if(count<0){
				Socket socket = channel.socket();
				channel.close();
				key.cancel();
				return;
			}
			if(count>0){
				buffer.flip();
				byte[] receivedData = new byte[count];
				System.arraycopy(buffer.array(), 0, receivedData, 0, count);
				String receivedStr = new String(receivedData);
				echo(channel, receivedStr);
			}
		}catch(Exception e){
			e.printStackTrace();
			try{
				key.channel().close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
			key.selector().wakeup();
		}
	}
	
	private void echo(SocketChannel channel, String echo) throws Exception{
		buffer.clear();
		buffer.put(echo.getBytes());
		buffer.flip();
		channel.write(buffer);
		
		System.out.println("echo:"+echo);
		
	}
	
	public static void main(String[] args) throws Exception {
		int port = PORT_NUMBER;
		NioEchoServer server = new NioEchoServer(port);
		server.start();
		server.join();
	}

}