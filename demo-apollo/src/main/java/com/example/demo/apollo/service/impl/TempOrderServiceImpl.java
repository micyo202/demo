package com.example.demo.apollo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.apollo.entity.TempOrder;
import com.example.demo.apollo.mapper.TempOrderMapper;
import com.example.demo.apollo.service.TempOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Order订单表 服务实现类
 * </p>
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @since 2020-05-12
 */
@Service
public class TempOrderServiceImpl extends ServiceImpl<TempOrderMapper, TempOrder> implements TempOrderService {

    @Override
    public List<TempOrder> selectByCustomSql() {
        return baseMapper.selectByCustomSql();
    }
}
