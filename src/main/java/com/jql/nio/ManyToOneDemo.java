package com.jql.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ManyToOneDemo {
    public static void main(String[] args) throws Exception {
        RandomAccessFile iRF = new RandomAccessFile("D:\\test\\1.txt","r");
        RandomAccessFile oRF = new RandomAccessFile("D:\\test\\2.txt","rw");



/*        FileChannel iChannel = iRF.getChannel();
        FileChannel oChannel = oRF.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocate(1024);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);

        ByteBuffer[] buffers = {buffer1,buffer2};

        iChannel.read(buffers);

        System.out.println(new String(buffer1.array(),0,buffer1.limit()));
        System.out.println("----------------------------------------------------");
        System.out.println(new String(buffer2.array(),0,buffer2.limit()));
        for (ByteBuffer buffer : buffers) {
            buffer.flip();
        }
        oChannel.write(buffers);*/

        byte[] data1 = new byte[1024];
        byte[] data2 = new byte[1024];
        int length1 = iRF.read(data1);
        int length2 = iRF.read(data2);
        System.out.println(new String(data1,0,length1));
        System.out.println("----------------------------------------------------");
        System.out.println(new String(data1,0,length2));

        byte[][] datas = new byte[][]{data1,data2};

        oRF.close();
        iRF.close();

    }
}
