package com.example.springCloud.util;

import com.alibaba.fastjson.JSON;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.IOException;


/**
 * 采用本地直连的方式
 * 或者采用阿里 cancel
 */
public class TestUtil {
    public static void main(String[] args) {
        BinaryLogClient client = new BinaryLogClient("47.92.235.151", 3306, "root", "123456");
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(new BinaryLogClient.EventListener() {

            @Override
            public void onEvent(Event event) {
                EventHeader header = event.getHeader();
                EventType eventType = header.getEventType();
                System.out.println(event.getData() instanceof UpdateRowsEventData);
                System.out.println(JSON.toJSONString(event.getData()));
                if (EventType.isWrite(eventType)) {
                    //获取事件体
                    WriteRowsEventData data = event.getData();
                    System.out.println(JSON.toJSONString(data));
                } else if (EventType.isUpdate(eventType)) {
                    UpdateRowsEventData data = event.getData();
                    System.out.println(JSON.toJSONString(data));
                } else if (EventType.isDelete(eventType)) {
                    DeleteRowsEventData data = event.getData();
                    System.out.println(JSON.toJSONString(data));
                }
            }
        });
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
