package com.jql.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileCopyDemo2 {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        FileInputStream inputStream = new FileInputStream("D:\\test\\3.zip");
        FileOutputStream outputStream = new FileOutputStream("D:\\test\\4.zip");

        FileChannel inChannel = inputStream.getChannel();
        FileChannel outChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (inChannel.read(buffer)!=-1){
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
        outChannel.close();
        inChannel.close();

        outputStream.close();
        inputStream.close();
        System.out.println("----------finish-------"+(System.currentTimeMillis()-start));

    }
}
