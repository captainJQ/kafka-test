package com.jql.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class ClientSocketDemo {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1",2222);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello world!".getBytes());
        InputStream inputStream = socket.getInputStream();
        byte[] data = new byte[1024];
        int len = inputStream.read(data);
        System.out.println("server-->client:"+new String(data,0,len));
        socket.close();
    }
}
