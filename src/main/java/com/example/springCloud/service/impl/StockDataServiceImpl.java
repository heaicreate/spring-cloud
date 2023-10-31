package com.example.springCloud.service.impl;

import com.example.springCloud.entity.StockData;
import com.example.springCloud.mapper.StockDataMapper;
import com.example.springCloud.service.StockDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 股票记录 服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2023-10-31
 */
@Service
public class StockDataServiceImpl extends ServiceImpl<StockDataMapper, StockData> implements StockDataService {

}
