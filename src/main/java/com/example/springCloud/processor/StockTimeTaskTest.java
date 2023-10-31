package com.example.springCloud.processor;

import org.joda.time.DateTime;
import us.codecraft.webmagic.Spider;

import java.util.Timer;
import java.util.TimerTask;

public class StockTimeTaskTest extends TimerTask {


    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new StockTimeTaskTest(), 0, 1000*60);

    }

    @Override
    public void run() {
        Spider.create(new StockProcessor()).addUrl("https://finance.pae.baidu.com/vapi/v1/getquotation?all=1&pointType=string&group=quotation_minute_ab&query=000155&code=000155").addPipeline(new StockPipeline()).thread(5).run();
        Spider.create(new StockProcessor()).addUrl("https://finance.pae.baidu.com/vapi/v1/getquotation?all=1&pointType=string&group=quotation_minute_ab&query=002466&code=002466").addPipeline(new StockPipeline()).thread(5).run();
        System.out.println("执行时间:" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

//        String date = DateTime.now().toString("yyyy-MM-dd");
//        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//        DateTime dateTime = dateTimeFormatter.parseDateTime(date + " 18:00:00");
//        DateTime dateTimeNow = DateTime.now();
//        Period period = new Period(dateTimeNow, dateTime);
//        String textSting = period.getHours() + "小时" + period.getMinutes() + "分" + period.getSeconds() + "秒";
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=07e22dbacea82936f6a7a4c0c820c56b603bf5f76787d0f794f849b26c3e1f57");
//        OapiRobotSendRequest request = new OapiRobotSendRequest();
//        request.setMsgtype("text");
//        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
//        text.setContent("下班:" + textSting);
//        request.setText(text);
//        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
//        // isAtAll类型如果不为Boolean，请升级至最新SDK
//        at.setIsAtAll(true);
//        request.setAt(at);
//
//
//        try {
//            OapiRobotSendResponse response = client.execute(request);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
    }
}
