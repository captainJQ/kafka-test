package com.jql.nio;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NIOFileCopyDemo {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        FileChannel inChannel = FileChannel.open(Paths.get("D:\\test\\3.zip"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("D:\\test\\4.zip"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);


        MappedByteBuffer inBuffer;
        MappedByteBuffer outBuffer;



/*        byte[] data = new byte[1024*1024*10];
        long position = 0;
        while (position<inChannel.size()-1024*1024*10){
            inBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, position, 1024*1024*10 );
            outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, position, 1024*1024*10 );
            inBuffer.get(data);
            outBuffer.put(data);
            position+=1024*1024*10;
        }
        inBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, position, inChannel.size()-position );
        outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, position, inChannel.size()-position );
        inBuffer.get(data,0,(int)(inChannel.size()-position));
        outBuffer.put(data,0,(int)(inChannel.size()-position));*/
        inBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size() );
        outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size() );
        byte[] data = new byte[inBuffer.limit()];
        inBuffer.get(data);
        outBuffer.put(data);

        outChannel.close();
        inChannel.close();

        System.out.println("----------finish-------"+(System.currentTimeMillis()-start));
    }
}
