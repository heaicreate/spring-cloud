package com.example.springCloud.util;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.joda.time.DateTime;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;
public class DirectBoss {

//    public static void main(String[] args) {
//
//        ConnectionFactory factory = new ConnectionFactory();
//        try {
//            factory.setHost("192.168.0.102");
//            factory.setPort(5672);
//            factory.setVirtualHost("/");
//            factory.setUsername("guest");
//            factory.setPassword("guest");
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            //声明交换机（名称和类型）
//            channel.exchangeDeclare("amq1.topic", BuiltinExchangeType.TOPIC);
//            String message = "直接交换模式j0,j1";
//            //消息发布（其中"directLogs"为交换机名称，"jay"为routingKey）
//            channel.basicPublish("amq1.topic", "66-12-12-12", null, message.getBytes());
//            System.out.println("********Message********:发送成功");
//            channel.close();
//            connection.close();
//        } catch (IOException | TimeoutException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        String startData="202202";
        String endData="202402";
        List<Integer> periods=new ArrayList<>();
        periods.add(Integer.valueOf(startData));
        Integer year=Integer.valueOf(startData.substring(0,4));
        Integer month=Integer.valueOf(startData.substring(4));
        DateTime dateTime1=new DateTime(year,month,1,0,0);
        while (!startData.equals(endData)){
            System.out.println(dateTime1.plusMonths(1).toString("yyyyMM"));
            periods.add(Integer.valueOf(dateTime1.plusMonths(1).toString("yyyyMM")));
            startData=dateTime1.plusMonths(1).toString("yyyyMM");
            System.out.println(startData);
            dateTime1=dateTime1.plusMonths(1);
        }
        System.out.println(periods);



    }

}
