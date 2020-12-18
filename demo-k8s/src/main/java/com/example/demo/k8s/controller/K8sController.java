package com.example.demo.k8s.controller;

import com.example.demo.k8s.runnable.PressureRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * K8sController
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2020/8/20
 */
@RestController
@Slf4j
public class K8sController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.application.name}")
    private String applicationName;

    private static Thread[] threads = null;

    @RequestMapping("/init")
    public String init() {
        long timestamp = Instant.now().toEpochMilli();
        String msg = "SUCCESS:\n" + String.format("%s (%s) - %d", applicationName, serverPort, timestamp) + "\n/pressure/{num}\n/release";
        log.info(msg);
        return msg;
    }

    @RequestMapping("/pressure/{num}")
    public String pressure(@PathVariable Integer num) {
        long timestamp = Instant.now().toEpochMilli();
        String msg = "WARNING:\n当前节点已有[" + num + "]个线程对 CPU 增压，请先释放 - " + timestamp;
        if (null == threads) {
            threads = new Thread[num];
            for (int i = 0; i < num; i++) {
                threads[i] = new Thread(new PressureRunnable());
                threads[i].start();
            }
            msg = "SUCCESS:\n设置[" + num + "]个线程对 CPU 进行增压 - " + timestamp;
        }
        log.info(msg);
        return msg;
    }

    @RequestMapping("/release")
    public String release() {
        long timestamp = Instant.now().toEpochMilli();
        String msg = "WARNING:\n当前节点没有对 CPU 增压，无需释放 - " + timestamp;
        if (null != threads) {
            for (Thread t : threads) {
                t.stop();
            }
            msg = "SUCCESS:\n释放[" + threads.length + "]个线程对 CPU 减压 - " + timestamp;
            threads = null;
        }
        log.info(msg);
        return msg;
    }

}
