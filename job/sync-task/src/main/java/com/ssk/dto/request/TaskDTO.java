package com.ssk.dto.request;

import lombok.Data;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-07-07
 */
@Data
public class TaskDTO {
    /**
     * 任务名
     */
    private String name;
    /**
     * 任务描述
     */
    private String desc;
    /**
     * 参数Json
     */
    private String params;
    /**
     * 重试次数
     */
    private Integer retry;
    /**
     * 回调url，目前只支持post回调
     */
    private String url;
    /**
     * 任务状态
     */
    private String status;
}
