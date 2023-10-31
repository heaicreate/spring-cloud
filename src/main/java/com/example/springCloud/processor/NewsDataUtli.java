package com.example.springCloud.processor;

import com.alibaba.fastjson.JSON;
import com.example.springCloud.entity.NewsData;
import com.mysql.jdbc.Driver;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.*;

@Slf4j
public class NewsDataUtli {

    /**
     * 查询标题是否存在
     *
     * @param title
     * @param newsSource
     * @return
     */
    public static NewsData newsDataQueryOrAdd(String title, Integer newsSource, Long num) {
        try {
            Connection connection = getNewDataConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * from news_data where title='%s' and news_source='%s'";
            ResultSet resultSet = statement.executeQuery(String.format(sql, title, newsSource));
            Map<String, Object> stringObjectMap = new HashMap<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            NewsData newsData = null;
            while (resultSet.next()) {//resultSet对象可能包含很多行数据，所以要是有while循环。
                for (int i = 1; i < count; i++) {
                    stringObjectMap.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
                }
                String json = JSON.toJSONString(stringObjectMap);
                newsData = JSON.parseObject(json, NewsData.class);
            }
            if (Objects.isNull(newsData)) {
                StringBuilder stringBuilder = new StringBuilder("insert into news_data (title,note,news_source,create_time,link_address,num) values");
                stringBuilder.append(String.format("('%s','%s','%s','%s','%s','%s')", title, title, newsSource, new DateTime().getMillis(), "", num));
                statement.execute(stringBuilder.toString());
            } else {
                //更新数量
                String stringUpdate = "update news_data set update_time=%s,num=%s where id =%s";
                stringUpdate = String.format(stringUpdate, new DateTime().getMillis(), num, newsData.getId());
                statement.execute(stringUpdate);
            }
            statement.close();
            connection.close();
            return newsData;
        } catch (SQLException e) {
            log.error("异常{}", e);
            return null;
        }
    }


    public static NewsData QueryNewsData() {
        try {
            Connection connection = getNewDataConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * from news_data order by num desc limit 1";
            ResultSet resultSet = statement.executeQuery(sql);
            Map<String, Object> stringObjectMap = new HashMap<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            NewsData newsData = null;
            while (resultSet.next()) {//resultSet对象可能包含很多行数据，所以要是有while循环。
                for (int i = 1; i < count; i++) {
                    stringObjectMap.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
                }
                String json = JSON.toJSONString(stringObjectMap);
                newsData = JSON.parseObject(json, NewsData.class);
            }
            if (Objects.nonNull(newsData)) {
                System.out.println("最新:"+JSON.toJSONString(newsData));
            }
            statement.close();
            connection.close();
            return newsData;
        } catch (SQLException e) {
            log.error("异常{}", e);
            return null;
        }
    }


    public static Connection getNewDataConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            /**
             * 2.通过DriverManager获取连接对象
             *
             * jdbc:mysql://：这是固定的写法，表示使用jdbc连接mysql数据库
             * localhost：ip地址，本地可以写成localhost。
             * 3306：mysql的端口号。
             * xia：数据库的名字。
             * 第一个root：mysql的用户名
             * 第二个root：mysql的密码。
             */
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&useSSL=false&characterEncoding=utf8", "root", "123456");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
