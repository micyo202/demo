package com.example.demo.graphql.graphql.resolver;

import com.example.demo.graphql.entity.TempOrder;
import com.example.demo.graphql.service.TempOrderService;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * OrderResolver
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/7/28
 */
@Component
public class OrderResolver implements GraphQLResolver<TempOrder> {

    @Autowired
    private TempOrderService tempOrderService;

    public List<TempOrder> getOrders() {
        return tempOrderService.selectByCustomSql();
    }
}