package com.example.demo.xxl.job.service.impl;

import com.example.demo.xxl.job.service.XxlJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * XxlJobServiceImpl
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/10/13
 */
@Slf4j
@Service
public class XxlJobServiceImpl implements XxlJobService {

    @Override
    public void test(String name) {
        log.info("测试业务方法，name = " + name);
    }

}
