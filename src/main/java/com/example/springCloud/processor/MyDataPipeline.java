package com.example.springCloud.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyDataPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        String demoData = resultItems.get("realtime");
        if (demoData == null) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(demoData).getJSONObject("data");
        JSONArray realTime = jsonObject.getJSONArray("realtime");
        for (int i = 0; i < realTime.size(); i++) {
            JSONObject jsonObjectRealTime = realTime.getJSONObject(i);
            NewsDataUtli.newsDataQueryOrAdd(jsonObjectRealTime.getString("note"), 0,jsonObjectRealTime.getLong("num"));
        }

    }
}
