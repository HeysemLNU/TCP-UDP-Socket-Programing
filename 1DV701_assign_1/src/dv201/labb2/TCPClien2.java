package dv201.labb2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClien2 {
    public static final String msg="Hello Every One2";
    public static void main(String[] args) throws IOException {
        byte [] buff=new byte[10];
        Socket hostSock=new Socket(args[0],Integer.valueOf(args[1]));

        OutputStream outStream=hostSock.getOutputStream();
        outStream.write(msg.getBytes(),0,msg.length());
        InputStream inputStream=hostSock.getInputStream();
        inputStream.read(buff);
        System.out.println(new String(buff));
        hostSock.close();

    }}
