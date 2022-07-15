package com.ssk.service;

import com.ssk.dto.request.CreateTaskRequest;
import com.ssk.dto.request.TaskDTO;
import com.ssk.dto.response.CreateTaskResponse;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-06-30
 */
public interface TaskService {

    /**
     * 初始化一个任务
     * @param request
     * @return
     */
    CreateTaskResponse create(CreateTaskRequest request);

    /**
     * 提交任务
     * @param taskDTO
     */
    void submitTask(TaskDTO taskDTO);

}
