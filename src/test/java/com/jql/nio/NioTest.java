package com.jql.nio;

import org.junit.Test;

import java.io.RandomAccessFile;

public class NioTest {

    @Test
    public void RandomAccessFileTest() throws Exception{
        RandomAccessFile raf = new RandomAccessFile("d:\\test\\1.txt","rw");
        String s = raf.readLine();
        System.out.println(s);
        s = raf.readLine();
        System.out.println(s);
        raf.close();
    }

}
