package com.ssk.listener;

import com.ssk.entity.TaskDO;

import java.util.Date;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-07-07
 */
public class DefaultTaskListener implements TaskListener{

    @Override
    public void success(TaskDO taskDO) {
        taskDO.setStatus("SUCCESS");
        taskDO.setUpdateAt(new Date());
        //TODO save
    }

    @Override
    public void error(TaskDO taskDO) {
        taskDO.setStatus("FAIL");
        taskDO.setUpdateAt(new Date());
        //TODO save
    }
}
