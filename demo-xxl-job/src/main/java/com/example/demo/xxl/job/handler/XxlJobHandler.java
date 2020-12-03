package com.example.demo.xxl.job.handler;

import com.example.demo.xxl.job.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * XxlJobHandler
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/10/13
 */
@Slf4j
@Component
public class XxlJobHandler {

    @Autowired
    private XxlJobService xxlJobService;

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public ReturnT<String> demoJobHandler(String param) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");

        xxlJobService.test("张三");

        for (int i = 0; i < 5; i++) {
            XxlJobLogger.log("beat at:" + i);
            log.info("开始执行任务" + i);
        }
        return ReturnT.SUCCESS;
    }

}
