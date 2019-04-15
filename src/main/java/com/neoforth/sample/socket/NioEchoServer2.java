package com.neoforth.sample.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;

public class NioEchoServer2 implements Runnable {

	// 어떤 채널이 어떤 IO를 할 수 있는지 알려주는 클래스(Seelctor)
	Selector selector;
	int port = 6000;

	// 한글 전송용
	Charset charset = Charset.forName("EUC-KR");
	CharsetEncoder encoder = charset.newEncoder();

	public NioEchoServer2() throws IOException {

		// Selector를 생성 합니다.
		selector = Selector.open();

		// ServerSocket에 대응하는 ServerSocketChannel을 생성, 아직 바인딩은 안됨
		ServerSocketChannel channel = ServerSocketChannel.open();

		// 서버 소켓 생성
		ServerSocket socket = channel.socket();

		SocketAddress addr = new InetSocketAddress(port);

		// 소켓을 해당 포트로 바인딩
		socket.bind(addr);

		// Non-Blocking 상태로 만듬
		channel.configureBlocking(false);

		// 바인딩된 ServerSocketChannel을 Selector에 등록 합니다.
		channel.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println("---- Client의 접속을 기다립니다... ----");
	}

	public void run() {
		// SocketChannel용 변수를 미리 만들어 둡니다.
		int socketOps = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;

		ByteBuffer buff = null;

		try {

			// 생성된 서버소켓채널에 대해 accept 상태 일때 알려달라고 selector에 등록 시킨 후
			// 이벤트가 일어날때 까지 기다립니다. 새로운 클라이언트가 접속하면 seletor는
			// 미리 등록 했던 SeerverSocketChannel에 이벤트가 발생했으므로 select 메소드에서
			// 1을 돌려줍니다. 즉 Selector에 감지된 이벤트가 있다면
			while (selector.select() > 0) {

				// 현재 selector에 등록된 채널에 동작이 라나라도 실행 되는 경우 그 채널들을 SelectionKey의
				// Set에 추가 합니다. 아래에서는 선택된 채널들의 키를 얻습니다. 즉 해당 IO에 대해 등록해
				// 놓은 채널의 키를 얻는 겁니다.
				Set keys = selector.selectedKeys();
				Iterator iter = keys.iterator();

				while (iter.hasNext()) {
					SelectionKey selected = (SelectionKey) iter.next();

					// 현재 처리하는 SelectionKey는 Set에서 제거 합니다.
					iter.remove();

					// channel()의 현재 하고 있는 동작(읽기, 쓰기)에 대한 파악을 하기 위한 겁니다.
					SelectableChannel channel = selected.channel();

					if (channel instanceof ServerSocketChannel) {

						// ServerSocketChannel이라면 accept()를 호출해서
						// 접속 요청을 해온 상대방 소켓과 연결 될 수 있는 SocketChannel을 얻습니다.
						ServerSocketChannel serverChannel = (ServerSocketChannel) channel;
						SocketChannel socketChannel = serverChannel.accept();

						// 현시점의 ServerSocketChannel은 Non-Blocking IO로 설정 되어 있습니다.
						// 이것은 당장 접속이 없어도 블로킹 되지 않고 바로 null을 던지므로
						// 체트 해야 합니다.
						if (socketChannel == null) {
							System.out.println("## null server socket");
							continue;
						}

						System.out.println("## socket accepted : " + socketChannel);

						// 얻어진 소켓은 블로킹 소켓이므로 Non-Blocking IO 상태로 설정 합니다.
						socketChannel.configureBlocking(false);

						// 소켓 채널을 Selector에 등록
						socketChannel.register(selector, socketOps);
					} else {
						// 일반 소켓 채널인 경우 해당 채널을 얻어낸다.
						SocketChannel socketChannel = (SocketChannel) channel;
						buff = ByteBuffer.allocate(100);

						// 소켓 채널의 행동을 검사해서 그에 대응하는 작업을 함
						if (selected.isConnectable()) {
							System.out.println("Client와의 연결 설정 OK~");
							if (socketChannel.isConnectionPending()) {
								System.out.println("Client와의 연결 설정을 마무리 중입니다~");
								socketChannel.finishConnect();
							}
						}
						// 읽기 요청 이라면
						else if (selected.isReadable()) {
							// 소켓 채널로 데이터를 읽어 들입니다.
							try {
								socketChannel.read(buff);

								// 데이터가 있다면
								if (buff.position() != 0) {
									buff.clear();

									System.out.print("클라이언트로 전달된 내용 : ");

									// Non-Blocking Mode이므로 데이터가 모두 전달될때 까지 기다림
									while (buff.hasRemaining()) {
										System.out.print((char) buff.get());
									}

									buff.clear();
									System.out.println();

									// 쓰기가 가능 하다면
									if (selected.isWritable()) {
										String str = "이건 서버에서 보낸 데이터...";

										// 한글 인코딩
										socketChannel.write(encoder.encode(CharBuffer.wrap(str + "\r\n")));
										System.out.println("서버가 전달한 내용 : " + str);
									}

								}
							} catch (IOException ioe) {
								ioe.printStackTrace();
								socketChannel.finishConnect();
								socketChannel.close();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		NioEchoServer2 s = new NioEchoServer2();
		new Thread(s).start();
	}
}
