package com.example.springCloud.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class StockProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).addHeader("accept","application/vnd.finance-web.v1+json").setSleepTime(1000).setTimeOut(10000);
    @Override
    public void process(Page page) {
        page.putField("news", page.getRawText());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
