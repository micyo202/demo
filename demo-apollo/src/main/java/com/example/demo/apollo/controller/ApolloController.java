package com.example.demo.apollo.controller;

import com.example.demo.apollo.entity.TempOrder;
import com.example.demo.apollo.service.TempOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ApolloController
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/9/3
 */
@RestController
@Slf4j
public class ApolloController {

    @Value("${server.port:server.port}")
    private String serverPort;

    @Value("${spring.application.name:spring.application.name}")
    private String applicationName;

    @Value("${common.name:common.name}")
    private String commonName;

    @Value("${foo:foo}")
    private String foo;

    @Value("#{'${list:null}'.split(',')}")
    private List<String> list;

    @Value("#{${map:null}}")
    private Map<String, String> map;

    @Value("${node.name:node.name}")
    private String nodeName;

    @RequestMapping("/init")
    public Map<String, Object> init() {
        Map<String, Object> resultMap = new HashMap<>(12);
        resultMap.put("version", "Apollo v1.7.1");
        resultMap.put("server.port", serverPort);
        resultMap.put("application.name", applicationName);
        resultMap.put("common.name", commonName);
        resultMap.put("foo", foo);
        resultMap.put("list", list);
        resultMap.put("map", map);
        resultMap.put("node.name", nodeName);
        System.out.println(resultMap);
        log.debug(String.valueOf(resultMap));
        log.info(String.valueOf(resultMap));
        log.warn(String.valueOf(resultMap));
        log.error(String.valueOf(resultMap));
        return resultMap;
    }

    @Autowired
    TempOrderService tempOrderService;

    @RequestMapping("/select")
    public List<TempOrder> select() {
        List<TempOrder> tempOrders = tempOrderService.selectByCustomSql();
        return tempOrders;
    }
}
