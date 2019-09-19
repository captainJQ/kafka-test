package com.jql.nio.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;

public class ServerHandlerDemo extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup cg = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        Iterator<Channel> iterator = cg.iterator();
        while (iterator.hasNext()){
            Channel next = iterator.next();
            if(next.equals(channel)){
                next.writeAndFlush( "[your words:]\t"+msg+"\r\n");
            }else {
                next.writeAndFlush( "["+channel.remoteAddress()+":]\t"+msg+"\r\n");
            }
        }
        System.out.print( "["+channel.remoteAddress()+":]\t"+msg+"\r\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive\t"+ctx.channel().remoteAddress());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        cg.add(ctx.channel());
        System.out.println("handlerAdded\t"+ctx.channel().remoteAddress());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved\t"+ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive\t"+ctx.channel().remoteAddress());
    }
}
