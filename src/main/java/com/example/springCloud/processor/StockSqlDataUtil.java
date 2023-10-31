package com.example.springCloud.processor;

import com.example.springCloud.entity.StockData;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
public class StockSqlDataUtil {

    public static StockData newsDataQueryOrAdd(String code, String priceLimitValue, String priceLimitStatus, Double highValue, Double nowPrice, String limitUp, Double openPrice) {
        try {
            Connection connection = NewsDataUtli.getNewDataConnection();
            Statement statement = connection.createStatement();
            StockData stockData = null;
            String codeName = "";
            if ("000155".equals(code)) {
                codeName="川能动力";
            }
            if ("002466".equals(code)) {
                codeName="天齐锂业";
            }
            if (Objects.isNull(stockData)) {
                StringBuilder stringBuilder = new StringBuilder("insert into stock_data (code_value,code_name,price_limit_value,price_limit_status,now_price,open_price,limit_up,create_time,create_day,high_price) values");
                stringBuilder.append(String.format("('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')", code, codeName, priceLimitValue, priceLimitStatus,nowPrice,openPrice, limitUp,new DateTime().getMillis(),new DateTime().toString("yyyy-MM-dd"),highValue));
                statement.execute(stringBuilder.toString());
            }
            statement.close();
            connection.close();
            return stockData;
        } catch (SQLException e) {
            log.error("异常{}", e);
            return null;
        }
    }
}
