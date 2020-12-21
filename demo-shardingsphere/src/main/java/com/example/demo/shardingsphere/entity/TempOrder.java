package com.example.demo.shardingsphere.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * Order订单表
 * </p>
 *
 * @author Yanzheng (https://github.com/micyo202)
 * @since 2020-05-12
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TempOrder extends Model<TempOrder> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Boolean valid;

    private Date createTime;

    private Date updateTime;
}
