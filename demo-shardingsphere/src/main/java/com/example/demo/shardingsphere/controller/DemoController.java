package com.example.demo.shardingsphere.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.shardingsphere.entity.TempOrder;
import com.example.demo.shardingsphere.service.TempOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * DemoController
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/7/16
 */
@RestController
public class DemoController {

    @Autowired
    private TempOrderService tempOrderService;

    @RequestMapping("/insert/{num}")
    public String insert(@PathVariable int num) {

        for (int i = 0; i < num; i++) {

            boolean valid = true;
            if (i % 2 == 0) {
                valid = false;
            }

            TempOrder tempOrder = new TempOrder();
            tempOrder
                    .setId(Instant.now().toEpochMilli() + i)
                    .setName("姓名-" + i)
                    .setValid(valid)
                    .setCreateTime(new Date())
                    .setUpdateTime(new Date());
            tempOrderService.save(tempOrder);
        }
        return "SUCCESS";
    }

    @RequestMapping("/select")
    public List<TempOrder> select() {
        List<TempOrder> tempOrders = tempOrderService.selectByCustomSql();
        QueryWrapper<TempOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "姓名-27");
        TempOrder one = tempOrderService.getOne(queryWrapper);
        TempOrder byId = tempOrderService.getById(1596508609259L);
        return tempOrders;
    }
}