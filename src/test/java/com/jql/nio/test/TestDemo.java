package com.jql.nio.test;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

/**
 * TestDemo
 *
 * @author laijunqiang
 * @data 2019/10/30
 */
public class TestDemo {

    @Test
    public void test1(){
        String date = DateFormatUtils.format(new Date(), "yyyy年MM月dd日");
        System.out.println(date);
    }

}
