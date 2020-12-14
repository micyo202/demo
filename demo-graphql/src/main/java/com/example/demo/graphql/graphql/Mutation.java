package com.example.demo.graphql.graphql;

import com.example.demo.graphql.entity.TempOrder;
import com.example.demo.graphql.graphql.model.TempOrderInput;
import com.example.demo.graphql.service.TempOrderService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mutation
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/7/28
 */
@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private TempOrderService tempOrderService;

    public TempOrder saveOrderWithInput(TempOrderInput input) {
        return saveOrder(input.getId(), input.getName(), input.getValid());
    }

    public TempOrder saveOrderWithParam(Long id, String name, Boolean valid) {
        return saveOrder(id, name, valid);
    }

    private TempOrder saveOrder(Long id, String name, Boolean valid) {

        if (null == id || id <= 0L) {
            id = (long) (Math.random() * (9999 - 1000 + 1) + 1000);
        }
        if (null == name || "".equals(name)) {
            name = "随机名称" + id;
        }
        if (null == valid) {
            valid = true;
        }

        TempOrder tempOrder = new TempOrder();
        tempOrder.setId(id);
        tempOrder.setName(name);
        tempOrder.setValid(valid);
        tempOrder.setCreateTime(LocalDateTime.now());
        tempOrder.setUpdateTime(LocalDateTime.now());
        tempOrderService.save(tempOrder);
        return tempOrder;
    }

}