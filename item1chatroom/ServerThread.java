package day1029.item1chatroom;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * @version 1.0
 * @auth刘伟
 */
public class ServerThread extends Thread{
    //当前线程对应的Socket
    Socket socket;
    //声明一个读取流
    BufferedReader br;
    //声明一个写入流
    BufferedWriter bw;
    public ServerThread(Socket socket1) {
        this.socket=socket1;
        try {
            br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        /*欢迎信息*/
        //读取客户端名字
        try {
            //因为客户端连接后才能操作所以直接获取就好 和输入对话不冲突
            String Cname= br.readLine();
            //把获取的名字设置到线程中
            this.setName(Cname);
           /*将欢迎的信息发送给所有人*/
            //设置欢迎信息
            String words = "欢迎"+Cname+"加入到群聊";
            //通过写入流写到网络流中并发给每个成员（遍历发送）
            for (ServerThread bst:ChatServer.stList) {
                //让每个服务端线程线程对象将信息写入到对应的客户端
                bst.bw.write(words);
                bst.bw.newLine();
                bst.bw.flush();

            }
            while(true) {
                //读取客户端发送的消息
                //这里不用担心会获取到名字
                String cotent = br.readLine();
                /*私聊*/
                //测试在指定索引处开始的此字符串的子字符串是否以指定的前缀开头。startWith
                if (cotent.startsWith("@")) {
                    for (int i = 0; i < ChatServer.stList.size(); i++) {
                        if (cotent.equals(ChatServer.stList.get(i).getName())) {
                            cotent = this.getName() + "悄悄对你说" + cotent;
                            bw.write(this.getName() + "悄悄对你说" + cotent);
                            bw.newLine();
                            bw.flush();
                            break;
                        }
                    }
                } else {

                    cotent = this.getName() + "说:" + cotent;
                    System.out.println(cotent);
                    //先实现群聊 群聊不用发送给自己所以先遍历集合
//                for (ServerThread f : ChatServer.stList) {
                    //因为是线程所以有名字而前面设置了名字
                    for (ServerThread bst : ChatServer.stList) {
                        if (!bst.getName().equals(this.getName())) {
                            bst.bw.write(cotent);
                            bst.bw.newLine();
                            bst.bw.flush();
                        }
                    }
//                }
                }
            }
        }catch (SocketException s){
            //先把这个线程从集合中剔除掉
//            for (ServerThread bst:ChatServer.stList){
//                if(bst.getName().equals(this.getName())){
//                    ChatServer.stList.remove(getName());
//                }
//            }
            for (int i = 0; i < ChatServer.stList.size(); i++) {
                if (ChatServer.stList.get(i).getName().equals(this.getName())) {
                    ChatServer.stList.remove(i);
                    break;
                }
            }
            for (ServerThread bst:ChatServer.stList) {
                try {
                    //把该线程退出的消息发给所有人
                    bst.bw.write(this.getName()+"退出聊天室");
                    bst.bw.newLine();
                    bst.bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
