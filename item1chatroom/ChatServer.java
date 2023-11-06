package day1029.item1chatroom;

import day1029.item1chatroom.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @auth刘伟
 */
public class ChatServer {
    public static List<ServerThread> stList =  new ArrayList<>();
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        System.out.println("等待第一位成员的到来");
        while (true) {
            //监听客户端，并接收客户端Socket
            Socket socket1 = ss.accept();
            System.out.println("来了一个新客户");
            //开启一个服务端线程与客户端进行交互
            ServerThread st =  new ServerThread(socket1);
            //将线程对象存到集合中
            stList.add(st);
            //启动线程
            st.start();
        }
    }
}
