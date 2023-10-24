package com.example.springCloud.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Timer;
import java.util.TimerTask;

public class TimeTaskTest extends TimerTask {


    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimeTaskTest(), 1000 * 60 * 10);
    }

    @Override
    public void run() {
        String date = DateTime.now().toString("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = dateTimeFormatter.parseDateTime(date + " 18:00:00");
        DateTime dateTimeNow = DateTime.now();
        Period period = new Period(dateTimeNow, dateTime);
        String textSting = period.getHours() + "小时" + period.getMinutes() + "分" + period.getSeconds() + "秒";
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=07e22dbacea82936f6a7a4c0c820c56b603bf5f76787d0f794f849b26c3e1f57");
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("下班:" + textSting);
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        // isAtAll类型如果不为Boolean，请升级至最新SDK
        at.setIsAtAll(true);
        request.setAt(at);


        try {
            OapiRobotSendResponse response = client.execute(request);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
