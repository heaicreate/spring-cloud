package com.example.springCloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springCloud.entity.Car;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alex wong
 * @since 2023-03-07
 */
@Mapper
public interface CarMapper extends BaseMapper<Car> {

}
