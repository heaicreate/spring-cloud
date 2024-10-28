package com.example.springCloud.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springCloud.entity.StockData;
import com.example.springCloud.processor.NewsDataUtli;
import lombok.Data;
import org.joda.time.DateTime;
import org.junit.Test;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StockQueryTest {

    @Test
    public void queryByDate() {
        String date = new DateTime().toString("yyyy-MM-dd");
        List<StockData> stockDataList = queryByData(date);
        Map<String, List<StockData>> stockDataMap = stockDataList.stream().collect(Collectors.groupingBy(StockData::getCodeValue));
        stockDataMap.forEach((k, v) -> {
            Map<Double, stockDateMapResp> stringstockDateMapRespMap = new HashMap<>();
            List<stockDateMapResp> stockDateMapResps = new ArrayList<>();
            v.forEach(s -> {
                stockDateMapResp stockDateMapResp = stringstockDateMapRespMap.get(s.getNowPrice());
                if (Objects.isNull(stockDateMapResp)) {
                    stockDateMapResp stockDateMapRespTemp = new stockDateMapResp();
                    stockDateMapRespTemp.setCreateTime(s.getCreateTime());
                    stockDateMapRespTemp.setMostNewDate(new DateTime(s.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                    stockDateMapRespTemp.setName(s.getCodeName());
                    stockDateMapRespTemp.setPrice(s.getNowPrice());
                    stockDateMapRespTemp.setNums(1);
                    stockDateMapRespTemp.setStatus(s.getPriceLimitStatus());
                    stringstockDateMapRespMap.put(s.getNowPrice(), stockDateMapRespTemp);
                    return;
                }
                stockDateMapResp.setNums(stockDateMapResp.getNums() + 1);
                if (Long.compare(stockDateMapResp.getCreateTime(), s.getCreateTime()) <= 1) {
                    stockDateMapResp.setCreateTime(s.getCreateTime());
                    stockDateMapResp.setMostNewDate(new DateTime(s.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                }
            });
            stringstockDateMapRespMap.forEach((k1, v1) -> {
                stockDateMapResps.add(v1);
            });
            System.out.println(stockDateMapResps.stream().sorted(Comparator.comparing(stockDateMapResp::getNums).reversed()).collect(Collectors.toList()).get(0));
            System.out.println(stockDateMapResps.stream().sorted(Comparator.comparing(stockDateMapResp::getPrice).reversed()).collect(Collectors.toList()).get(0));
//            List<StockData> stockDataDistinct = v.stream().filter(distinctByKey1(s -> s.getNowPrice())).collect(Collectors.toList());
        });
    }


    /**
     * 9点20左右卖出
     * 接近3点买入
     */
    @Test
    public void queryLastMaxByDate() {
        String date = new DateTime().toString("yyyy-MM-dd");
        List<StockData> stockDataList = queryByData(date);
        Map<String, List<StockData>> stockDataMap = stockDataList.stream().collect(Collectors.groupingBy(StockData::getCodeValue));
        stockDataMap.forEach((k, v) -> {
            Map<Double, stockDateMapResp> stringstockDateMapRespMap = new HashMap<>();
            StockData stockDataListMax = v.stream().max(Comparator.comparing(StockData::getNowPrice)).get();
            StockData stockDataListMin = v.stream().min(Comparator.comparing(StockData::getNowPrice)).get();
            v.forEach(s -> {
                stockDateMapResp stockDateMapResp = stringstockDateMapRespMap.get(s.getNowPrice());
                if (Objects.isNull(stockDateMapResp)) {
                    stockDateMapResp stockDateMapRespTemp = new stockDateMapResp();
                    stockDateMapRespTemp.setCreateTime(s.getCreateTime());
                    stockDateMapRespTemp.setMostNewDate(new DateTime(s.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                    stockDateMapRespTemp.setName(s.getCodeName());
                    stockDateMapRespTemp.setPrice(s.getNowPrice());
                    stockDateMapRespTemp.setNums(1);
                    stockDateMapRespTemp.setStatus(s.getPriceLimitStatus());
                    stringstockDateMapRespMap.put(s.getNowPrice(), stockDateMapRespTemp);
                    return;
                }
                stockDateMapResp.setNums(stockDateMapResp.getNums() + 1);
                if (Long.compare(stockDateMapResp.getCreateTime(), s.getCreateTime()) <= 1) {
                    stockDateMapResp.setCreateTime(s.getCreateTime());
                    stockDateMapResp.setMostNewDate(new DateTime(s.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                }
            });
            stockDateMapResp stockDateMapRespTemp=stringstockDateMapRespMap.get(stockDataListMax.getNowPrice());
            stockDateMapResp stockDateMapRespMinTemp=stringstockDateMapRespMap.get(stockDataListMin.getNowPrice());
            System.out.println("最高:"+stockDateMapRespTemp);
            System.out.println("最低:"+stockDateMapRespMinTemp);
//            List<StockData> stockDataDistinct = v.stream().filter(distinctByKey1(s -> s.getNowPrice())).collect(Collectors.toList());
        });
    }


    @Data
    class stockDateMapResp {
        private String mostNewDate;
        private Integer nums;
        private Double price;
        private String status;
        private String name;
        private Long createTime;
    }

    static <T> Predicate<T> distinctByKey1(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public List<StockData> queryByData(String data) {
        try {
            List<StockData> stockDataList = new ArrayList<>();
            Connection connection = NewsDataUtli.getNewDataConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * from stock_data where create_day='%s'";
            ResultSet resultSet = statement.executeQuery(String.format(sql, data));
            Map<String, Object> stringObjectMap = new HashMap<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            StockData stockData = null;
            while (resultSet.next()) {//resultSet对象可能包含很多行数据，所以要是有while循环。
                for (int i = 1; i < count; i++) {
                    stringObjectMap.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
                }
                String json = JSON.toJSONString(stringObjectMap);
                stockData = JSON.parseObject(json, StockData.class);
                stockDataList.add(stockData);
            }

            statement.close();
            connection.close();
            return stockDataList;
        } catch (SQLException e) {
            return null;
        }
    }

}
