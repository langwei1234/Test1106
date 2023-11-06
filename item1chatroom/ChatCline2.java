package day1029.item1chatroom;

import java.io.IOException;
import java.net.Socket;

/**
 * @version 1.0
 * @auth刘伟
 */
public class ChatCline2 {
    public static void main(String[] args) throws IOException {
        Socket socket1 = new Socket("10.36.108.124",8888);
        ReadThread rt = new ReadThread(socket1);
        WriterThread st = new WriterThread(socket1);
        rt.start();
        st.start();
    }
}
