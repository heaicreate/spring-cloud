package com.example.springCloud.algorithm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springCloud.util.HttpUtil;
import com.example.springCloud.util.MysqlUtil;
import com.example.springCloud.util.model.DateModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@Slf4j
public class HttpTest {
    @Test
    public void testH1() {
        String url = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=ssq&pageNo=1&pageSize=300&systemType=PC";
        String s = null;
        JSONObject jsonObject = null;
        try {
            s = HttpUtil.doGet(String.format(url, 1));
            jsonObject = JSON.parseObject(s);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        try {
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < jsonObject.getInteger("pageNum"); i++) {
            try {
                s = HttpUtil.doGet(String.format(url, i));
            } catch (IOException e) {
                log.info(e.getMessage());
            }
            jsonObject = JSON.parseObject(s);
            List<DateModel> dateModels = JSON.parseArray(jsonObject.getString("result"), DateModel.class);
            Connection connection = MysqlUtil.getConnection();
            MysqlUtil.insertData(dateModels, connection);
            log.info(dateModels.toString());
        }

    }
}
