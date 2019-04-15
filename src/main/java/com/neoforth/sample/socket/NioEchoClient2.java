package com.neoforth.sample.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class NioEchoClient2 {
	public static void main(String[] args) throws IOException {
		Socket sock = null;
		try {
			sock = new Socket("127.0.0.1", 6000);
			System.out.println(sock + ": 연결됨");
			OutputStream toServer = sock.getOutputStream();
			InputStream fromServer = sock.getInputStream();

			byte[] buf = new byte[1024];
			int count;
			while ((count = System.in.read(buf)) != -1) {
				toServer.write(buf, 0, count);
				count = fromServer.read(buf);
				System.out.write(buf, 0, count);
			}
			toServer.close();
			while ((count = fromServer.read(buf)) != -1)
				System.out.write(buf, 0, count);
			System.out.close();
			System.out.println(sock + ": 연결 종료");
		} catch (IOException ex)

		{
			System.out.println("연결 종료 (" + ex + ")");
		} finally {
			try {
				if (sock != null)
					sock.close();
			} catch (IOException ex) {
			}
		}
	}
}
