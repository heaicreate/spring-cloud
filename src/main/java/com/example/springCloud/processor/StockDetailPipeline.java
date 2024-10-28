package com.example.springCloud.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class StockDetailPipeline implements Pipeline {
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
        JSONArray askinfo = jsonObject.getJSONArray("detailinfos");
        Type type = new TypeToken<List<Detailinfos>>() {
        }.getType();
        List<Detailinfos> detailinfos = new Gson().fromJson(askinfo.toJSONString(), type);

        detailinfos.forEach(s-> System.out.println(s.getPrice()+"--"+s.getFormatTime()));
        String code = basicinfosJSON.getString("code");
        String priceLimitStatus = "";
        for (int i = 0; i < pankouinfosList.size(); i++) {
            JSONObject pankouinfo = pankouinfosList.getJSONObject(i);
            String ename = pankouinfo.getString("ename");
            if (ename.equals("priceLimit")) {
                priceLimitStatus = pankouinfo.getString("status");
                break;
            }
        }
        //计算指定价格占比
        Double price = detailinfos.get(detailinfos.size() - 1).getPrice();
        int max = detailinfos.stream().filter(s -> Double.compare(s.getPrice(), price) >= 0).collect(Collectors.toList()).size();
        int min = detailinfos.stream().filter(s -> Double.compare(s.getPrice(), price) < 0).collect(Collectors.toList()).size();
        System.out.println(priceLimitStatus + "," + code + "," + price + "," + max + "," + min);
    }
}
