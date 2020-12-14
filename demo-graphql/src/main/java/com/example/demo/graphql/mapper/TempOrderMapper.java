package com.example.demo.graphql.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.graphql.entity.TempOrder;

import java.util.List;

/**
 * <p>
 * Order订单表 Mapper 接口
 * </p>
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @since 2020-05-12
 */
public interface TempOrderMapper extends BaseMapper<TempOrder> {

    List<TempOrder> selectByCustomSql();
}
