package com.example.springCloud.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        page.putField("demoData", page.getHtml().get());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://pdai.tech/md/interview/x-interview.html#_3-1-%E5%B9%B6%E5%8F%91%E5%9F%BA%E7%A1%80").addPipeline(new MyDataPipeline()).thread(5).run();
    }
}
