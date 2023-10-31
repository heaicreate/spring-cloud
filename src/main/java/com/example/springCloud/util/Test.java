package com.example.springCloud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.utils.ParamUtil;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
//        String projectPath = System.getProperty("user.dir");
//        System.out.println(projectPath);



        String s="{\n" +
                "  \"id\": 55,\n" +
                "  \"groupName\": \"检测测试人员默认权限\",\n" +
                "  \"purviewGroupKey\": \"JC-test\",\n" +
                "  \"remark\": \"\",\n" +
                "  \"permissionsName\": \"加盟中心、立项中台\",\n" +
                "  \"upPurviewGroupId\": 0,\n" +
                "  \"addPurviewGroupProducts\": [\n" +
                "    \"立项中台\"\n" +
                "  ],\n" +
                "  \"addPurviewGroupMenuReqs\": [\n" +
                "    {\n" +
                "      \"menuId\": 1622\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1652\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1482,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1727,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1728,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1729,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1730,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1731,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1732,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1733,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1749,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1750,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1751,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1752,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1753,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1754,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1756,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1757,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1758,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1759,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1760,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1761,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1762,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1764,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1765,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1767,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1768,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1769,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1771,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1772,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1773,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1774,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1775,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1776,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1777,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1779,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1780,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 1919,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 2080,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11738,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11913,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11914,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11915,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11916,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11917,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11934,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11951,\n" +
                "      \"da taScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11952,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11954,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11955,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 11963,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 12067,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 12068,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 12069,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 12071,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 12072,\n" +
                "      \"dataScope\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"menuId\": 12290,\n" +
                "      \"dataScope\": 0\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JSONObject jsonObject= JSON.parseObject(s);

        JSONArray jsonArray=jsonObject.getJSONArray("addPurviewGroupMenuReqs");
        List<Long> menuIds=new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            menuIds.add(jsonArray.getJSONObject(i).getLong("menuId"));
        }
        System.out.println(menuIds);

        int longingTaskCount = (int)Math.ceil((double)2000 / ParamUtil.getPerTaskConfigSize());
        System.out.println(longingTaskCount);
    }
}
