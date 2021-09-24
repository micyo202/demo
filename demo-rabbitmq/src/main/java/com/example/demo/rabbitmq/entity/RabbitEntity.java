package com.example.demo.rabbitmq.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * RabbitEntity
 * TODO
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @date 2021/9/14
 */
@Data
@Accessors(chain = true)
public class RabbitEntity implements Serializable {
    private int id;
    private String name;
}
