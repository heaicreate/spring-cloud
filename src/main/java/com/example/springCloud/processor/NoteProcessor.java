package com.example.springCloud.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class NoteProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(2).addHeader("cookie","SUB=_2AkMTpTKQf8NxqwJRmPATxG_hZIhxywzEieKl-cNLJRMxHRl-yT9kqn0gtRB6OCUcfyhEEfec2ThbefDicd3_G0mS39qW; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9Wh8KXYrS4IWTh1T-CVn1jKf; _s_tentry=weibo.com; Apache=1410512287646.708.1698197640957; SINAGLOBAL=1410512287646.708.1698197640957; ULV=1698197640961:1:1:1:1410512287646.708.1698197640957:").setSleepTime(1000).setTimeOut(10000);
    @Override
    public void process(Page page) {
        page.putField("note", page.getRawText());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
