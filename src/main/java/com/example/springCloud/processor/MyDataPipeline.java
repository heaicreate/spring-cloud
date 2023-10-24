package com.example.springCloud.processor;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyDataPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        String demoData = resultItems.get("demoData");
        if (demoData != null) {
            System.out.println(demoData);
        }
    }
}
