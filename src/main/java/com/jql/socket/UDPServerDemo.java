package com.jql.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.channels.DatagramChannel;

public class UDPServerDemo {

    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();

        DatagramChannel channel = socket.getChannel();

        byte[] data = "www.smy.com".getBytes();
        DatagramPacket packet = new DatagramPacket(data,data.length, InetAddress.getByName("localhost"),2222);
        socket.send(packet);
        data = new byte[1024];
        DatagramPacket result = new DatagramPacket(data,0,data.length);
        socket.receive(result);
        System.out.println(new String(data,0,result.getLength()));
        socket.close();
    }
}
