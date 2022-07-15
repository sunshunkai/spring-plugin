package com.ssk.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-06-30
 */
@Data
public abstract class BaseEntity implements Serializable {

    private Long id;

    private String createBy;

    private String updateBy;

    private Date createAt;

    private Date updateAt;
}
