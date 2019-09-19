package com.jql.nio.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientDemo {
    public static void main(String[] args) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitDemo());
            Channel channel = bootstrap.connect("127.0.0.1", 2222).sync().channel();
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
            String message = null;
            while (!"&quit".equals(message=buf.readLine())){
                channel.writeAndFlush(message+"\r\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
