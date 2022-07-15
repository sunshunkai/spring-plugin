package com.ssk.listener;

import com.ssk.entity.TaskDO;

/**
 * 任务执行监听
 *
 * @description:
 * @author: sksun2
 * @create: 2022-07-07
 */
public interface TaskListener {

    void success(TaskDO taskDO);

    void error(TaskDO taskDO);

}
