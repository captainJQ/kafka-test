package com.jql.socket;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(2222);
        System.out.println("server start...");
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        byte[] data = new byte[1024];
        int len = inputStream.read(data);
        System.out.println("server recived from "+accept.getInetAddress()+":"+accept.getPort()+"\t:"+new String(data,0,len));
        accept.getOutputStream().write("accepted....".getBytes());
        System.out.println("send to client \"accepted....\"");
        accept.close();
        serverSocket.close();
    }
}
