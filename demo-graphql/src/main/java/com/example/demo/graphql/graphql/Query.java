package com.example.demo.graphql.graphql;

import com.example.demo.graphql.entity.TempOrder;
import com.example.demo.graphql.service.TempOrderService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Query
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/7/28
 */
@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private TempOrderService tempOrderService;

    public List<TempOrder> getAllOrders() {
        return tempOrderService.list();
    }

    public TempOrder getOrderById(Long id) {
        return tempOrderService.getById(id);
    }
}
