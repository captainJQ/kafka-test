package com.jql.socket;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;

public class UDPClientDemo {
    private static HashMap<String,String> host;
    static {
        host = new HashMap<>();
        host.put("www.jd.com","192.168.1.1");
        host.put("www.baidu.com","192.168.1.2");
        host.put("www.smy.com","192.168.1.3");
    }
    public static void main(String[] args) throws Exception {
        DatagramSocket server = new DatagramSocket(2222);
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data,data.length);
        System.out.println("dns server started....");
        server.receive(packet);
        String message = new String(data, 0, packet.getLength());
        System.out.println("received :\t"+message);
        byte[] sendData = host.get(message).getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,packet.getAddress(),packet.getPort());
        server.send(sendPacket);
        server.close();
    }
}
