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

/**
 * 案例设计:
 * 
 * Server：S
 * 
 * Client：clientA， clientB，clientC
 * 
 * Client端行为：
 * 
 * ******提示信息后续添加******
 * 
 * 1. 输入任意字符S表示“登录”请求
 * 
 * 2. S返回提示输入标识号
 * 
 * 3.输入标识号---S判断是否唯一，返回判断结果，重新输入标识号，直到标志号合法
 * 
 * 4. 任意一个客户端登录，提示所有客户端，连接的客户端个数和列表。
 * 
 * 5.发送消息，clientA发送一个消息，clientB，clientC显示clientA发送的消息内容，同理
 * 
 * 6.成功了，clientA收到S发送的反馈。
 * 
 * 7.每个客户端在不同的情况下处理不同（定时、sleep）
 * 
 * @author SMF
 * @time 2018年1月22日
 */
public class Server extends ServerSocket {

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
	public Server() throws IOException {
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
							this.sendMessage("加入成功！");
							// 客户端等于或者超过两个的情况
							if (null != tl && tl.size() > 1) {
								for (HandlerThread t : tl) {
									t.sendMessage("当前加入的客户端个数为 ：" + clientSet.size());
									t.sendMessage("加入的客户端为 ：" + clientSet.toString());
								}
							} else {
								this.sendMessage("当前只有一个客户端加入！");
							}
						}
					} else {// 不是第一次登录
						if (null != tl && tl.size() > 1) {
							// 客户端等于或者超过两个的情况
							this.pushMessage("客户端" + this.identity_card + "发送消息　：　" + clientIn);
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
			} finally {// Client退出

				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				clientSet.remove(identity_card);
				tl.remove(this);
				// pushMessage("Client<" + identity_card + ">退出");
				if (null != tl && tl.size() > 0) {
					for (HandlerThread t : tl) {
						t.sendMessage("客户端" + this.identity_card + "退出！");
						t.sendMessage("当前没有退出的客户端个数为 ：" + clientSet.size());
						t.sendMessage("没有退出的客户端为 ：" + clientSet.toString());
					}

				}
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