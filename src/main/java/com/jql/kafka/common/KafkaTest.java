package com.jql.kafka.common;

import org.junit.Test;

import java.util.Scanner;

public class KafkaTest {


    public static void main(String[] args) {
        Scanner type = new Scanner(System.in);
        String message = null;
        while (!"#q".equals(message = type.nextLine())) {
            KafkaProductor.send("buy",message);
        }
        type.close();
    }

    @Test
    public void test(){
        Scanner type = new Scanner(System.in);
        String message = null;
        while (!"#q".equals(message = type.nextLine())) {
            KafkaProductor.send("buy",message);
        }
        type.close();
    }

    @Test
    public void test2() throws InterruptedException {
        new Thread(new BuyKafkaConsumer()).start();
        Thread.sleep(100000);
    }

}
