package com.ssk.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-06-30
 */
@Data
public class CreateTaskResponse implements Serializable {
    /**
     * 创建任务id
     */
    private Long taskId;
}
