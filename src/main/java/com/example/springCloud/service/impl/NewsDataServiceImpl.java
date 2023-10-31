package com.example.springCloud.service.impl;

import com.example.springCloud.entity.NewsData;
import com.example.springCloud.mapper.NewsDataMapper;
import com.example.springCloud.service.NewsDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻数据 服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2023-10-25
 */
@Service
public class NewsDataServiceImpl extends ServiceImpl<NewsDataMapper, NewsData> implements NewsDataService {

}
