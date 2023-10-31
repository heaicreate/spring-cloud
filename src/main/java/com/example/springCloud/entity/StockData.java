package com.example.springCloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 股票记录
 * </p>
 *
 * @author alex wong
 * @since 2023-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StockData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 代码code
     */
    private String codeValue;

    /**
     * 名称
     */
    private String codeName;

    /**
     * 涨跌幅
     */
    private String priceLimitValue;

    /**
     * 状态
     */
    private String priceLimitStatus;

    /**
     * 现价
     */
    private Double nowPrice;

    /**
     * 今开
     */
    private Double openPrice;

    /**
     * 涨停
     */
    private Double limitUp;
    /**
     * 最高价
     */
    private Double highPrice;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 创建日期
     */
    private String createDay;


}
