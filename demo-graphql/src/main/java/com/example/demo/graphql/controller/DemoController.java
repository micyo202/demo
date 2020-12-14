package com.example.demo.graphql.controller;

import com.example.demo.graphql.entity.TempOrder;
import com.example.demo.graphql.service.TempOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
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
                    .setCreateTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());
            tempOrderService.save(tempOrder);
        }
        return "SUCCESS";
    }

    @RequestMapping("/select")
    public List<TempOrder> select() {
        List<TempOrder> tempOrders = tempOrderService.selectByCustomSql();
        return tempOrders;
    }
}