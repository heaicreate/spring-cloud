package com.example.springCloud.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Slf4j
public class BlockPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        String demoData = resultItems.get("news");
        if (demoData == null) {
            return;
        }
        String soutString = "%s,%s,%s,%s,%s";
        JSONArray result = JSON.parseObject(demoData).getJSONArray("Result");
        JSONObject jsonObjectResult = result.getJSONObject(0).getJSONArray("blocks").getJSONObject(0);
        JSONArray listArrays = jsonObjectResult.getJSONArray("list");
        for (int i = 0; i < listArrays.size(); i++) {
            JSONObject jsonObject = listArrays.getJSONObject(i);
            String blockName = jsonObject.getString("name");
            JSONArray riseFirstList = jsonObject.getJSONArray("rise_first");
            for (int j = 0; j < riseFirstList.size(); j++) {
                JSONObject jsonObjectRise = riseFirstList.getJSONObject(j);
                JSONObject jsonObjectRatio = jsonObjectRise.getJSONObject("ratio");
                JSONObject jsonObjectPrice = jsonObjectRise.getJSONObject("price");
                System.out.println("\033[31m" + String.format(soutString, blockName, jsonObjectRise.getString("code"), jsonObjectRise.getString("name"), jsonObjectRatio.getString("value"),jsonObjectPrice.getString("value")) + "\033[0m");
                break;
            }

        }


    }
}
