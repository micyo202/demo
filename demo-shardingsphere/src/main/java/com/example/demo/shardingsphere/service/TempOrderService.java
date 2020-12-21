package com.example.demo.shardingsphere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.shardingsphere.entity.TempOrder;

import java.util.List;

/**
 * <p>
 * Order订单表 服务类
 * </p>
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @since 2020-05-12
 */
public interface TempOrderService extends IService<TempOrder> {

    List<TempOrder> selectByCustomSql();
}
