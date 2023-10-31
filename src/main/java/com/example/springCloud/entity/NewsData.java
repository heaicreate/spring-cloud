package com.example.springCloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 新闻数据
 * </p>
 *
 * @author alex wong
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewsData implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 文本内容
     */
    private String note;

    /**
     * 来源 0:新浪微博
     */
    private Integer newsSource;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 链接地址
     */
    private String linkAddress;


}
