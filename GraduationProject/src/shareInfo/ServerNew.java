package shareInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerNew extends ServerSocket {

	private static final int SERVER_PORT = 5678;

	private static boolean isPrint = false;// 是否输出消息标志
	private static CopyOnWriteArrayList<HandlerThread> tl = new CopyOnWriteArrayList<HandlerThread>();// 服务器已启用线程集合
	private static CopyOnWriteArraySet<String> clientSet = new CopyOnWriteArraySet<String>();// 保存上线的客户端的唯一标识号

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		new Server();// 启动服务端
	}

	/**
	 * 创建服务端Socket,创建向客户端发送消息线程,监听客户端请求并处理
	 */
	public ServerNew() throws IOException {
		super(SERVER_PORT);// 创建ServerSocket

		try {
			while (true) {// 监听客户端请求，启个线程处理
				Socket socket = accept();
				new HandlerThread(socket);

			}
		} catch (Exception e) {
		} finally {
			close();
		}
	}

	class HandlerThread extends Thread {

		private Socket client;
		private PrintWriter out;
		private BufferedReader in;
		private String identity_card;
		private LinkedBlockingQueue<String> msgs = new LinkedBlockingQueue<String>();// 存放消息队列

		public HandlerThread(Socket s) throws IOException {
			client = s;
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			in.readLine();
			out.println("成功连上,请输入标识名：");
			start();
		}

		@Override
		public void run() {
			try {
				int flag = 0;
				String clientIn = in.readLine();
				while (!"BYE".equals(clientIn)) {
					if (flag == 0) { // flag == 0 此客户端第一次登录
						identity_card = clientIn;
						if (clientSet.contains(identity_card)) {
							this.sendMessage("该标识名已经存在，请重新输入 ： ");
						} else {
							flag++;
							clientSet.add(identity_card);
							tl.add(this);
							this.sendMessage("上线成功！");
							// 客户端等于或者超过两个的情况
							if (null != tl && tl.size() > 1) {
								this.sendMessage("当前在线客户端个数为 ：" + clientSet.size());
								this.sendMessage("在线客户端为 ：" + clientSet.toString());
							} else {
								this.sendMessage("当前只有一个客户端在线！");
							}
						}
					} else {// 不是第一次登录
						if (null != tl && tl.size() > 1) {
							// 客户端等于或者超过两个的情况
							this.pushMessage("客户端" + this.identity_card + "发送消息" + clientIn);
							isPrint = msgs.size() > 0 ? true : false;
							while (isPrint) {
								String msg = msgs.poll();
								for (HandlerThread t : tl) {
									if (t != this) {
										t.sendMessage(msg);
									}
								}
								isPrint = msgs.size() > 0 ? true : false;
							}
						}
					}
					clientIn = in.readLine();
				}
				out.println("byeClient");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {// 用户退出聊天室
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				tl.remove(this);
				clientSet.remove(identity_card);
				pushMessage("Client<" + identity_card + ">退出");
			}

		}

		// 放入消息队列末尾，准备发送给客户端
		private void pushMessage(String msg) {
			msgs.offer(msg);
			isPrint = true;
		}

		// 向客户端发送一条消息
		private void sendMessage(String msg) {
			if (null != msg) {
				out.println(msg);
			}
		}

	}

}
