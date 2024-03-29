package com.example.springCloud.util;

import com.example.springCloud.processor.GithubRepoPageProcessor;
import com.example.springCloud.processor.MyDataPipeline;
import com.example.springCloud.processor.NewsDataUtli;
import org.joda.time.DateTime;
import us.codecraft.webmagic.Spider;

import java.util.Timer;
import java.util.TimerTask;

public class TimeTaskTest extends TimerTask {


    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimeTaskTest(), 1000, 1000 * 60);

    }

    @Override
    public void run() {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://weibo.com/ajax/side/hotSearch").addPipeline(new MyDataPipeline()).thread(5).run();
        System.out.println("执行时间:" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        NewsDataUtli.QueryNewsData();
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
