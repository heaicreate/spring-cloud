package com.example.springCloud.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Slf4j
public class StockPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        String demoData = resultItems.get("news");
        if (demoData == null) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(demoData).getJSONObject("Result");
        JSONObject basicinfosJSON = jsonObject.getJSONObject("basicinfos");
        JSONObject pankouinfosJSON = jsonObject.getJSONObject("pankouinfos");
        JSONArray pankouinfosList = pankouinfosJSON.getJSONArray("list");
        JSONArray askinfo = jsonObject.getJSONArray("askinfos");
        JSONObject askinfoObject = askinfo.getJSONObject(0);
        //获取涨幅
        //000155-川能动力
        //002466-天齐锂业
        String code = basicinfosJSON.getString("code");
        //涨跌幅
        String priceLimitValue = "";
        //状态
        String priceLimitStatus = "";
        //今天最高
        Double highValue = 0.000;
        Double openPrice = 0.000;
        //现价
        Double nowPrice = askinfoObject.getDouble("askprice");
        //涨停
        String limitUp = "";
        for (int i = 0; i < pankouinfosList.size(); i++) {
            JSONObject pankouinfo = pankouinfosList.getJSONObject(i);
            String ename = pankouinfo.getString("ename");
            if (ename.equals("priceLimit")) {
                priceLimitValue = pankouinfo.getString("value");
                priceLimitStatus = pankouinfo.getString("status");
            }
            if (ename.equals("limitUp")) {
                limitUp = pankouinfo.getString("value");
            }
            if (ename.equals("high")) {
                highValue = pankouinfo.getDouble("value");
            }
            if (ename.equals("open")) {
                openPrice = pankouinfo.getDouble("value");
            }
        }
        StockSqlDataUtil.newsDataQueryOrAdd(code,priceLimitValue,priceLimitStatus,highValue,nowPrice,limitUp,openPrice);
        /**
         * String BLACK_FG = "30";
         * String RED_FG = "31";
         * String GREEN_FG = "32";
         * String YELLOW_FG = "33";
         * String BLUE_FG = "34";
         * String MAGENTA_FG = "35";
         * String CYAN_FG = "36";
         * String WHITE_FG = "37";
         * String DEFAULT_FG = "39";
         */
        String stringFormat = "涨跌幅:%S,状态:%s,今天最高:%s,现价:%s,涨停:%s,今开:%s";
        if (Double.compare(nowPrice, highValue) >= 1) {
            System.out.println("\033[31m" + String.format(stringFormat, priceLimitValue, priceLimitStatus, highValue, nowPrice, limitUp,openPrice + "\033[0m"));
        } else {
            System.out.println("\033[32m" + String.format(stringFormat, priceLimitValue, priceLimitStatus, highValue, nowPrice, limitUp,openPrice + "\033[0m"));
        }
//        if ("000155".equals(code)) {
//            System.out.println("" + priceLimitValue + "" + limitUp + "" + priceLimitStatus);
//        }
//        if ("002466".equals(code)) {
//            System.out.println("" + priceLimitValue + "" + limitUp + "" + priceLimitStatus);
//        }


    }
}
