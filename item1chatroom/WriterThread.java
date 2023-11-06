package day1029.item1chatroom;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @version 1.0
 * @auth刘伟
 */
public class WriterThread extends Thread{
    Socket socket ;
    //声明写入流
    public WriterThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner sc = new Scanner(System.in);
            //这里要先输入用户名方便服务器接收
            System.out.println("请输入用户名:");
            String Cname = sc.nextLine();
            //给线程设置名字并传到流中
//            this.setName(Cname);
            bw.write(Cname);
            bw.newLine();
            bw.flush();
            //要一直循环输入
            while (true) {
                String words = sc.nextLine();
                bw.write(words);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
