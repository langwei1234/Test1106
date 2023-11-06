package day1029.item1chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @version 1.0
 * @auth刘伟
 */
public class ReadThread extends Thread{
    //声明Socket
    Socket socket;
    //声明字符读取流

    public ReadThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String reciveWords = br.readLine();
                System.out.println(reciveWords);
            }
        } catch (IOException e) {
            System.out.println("聊天室已解散");
            System.exit(0);
        }
    }
}
