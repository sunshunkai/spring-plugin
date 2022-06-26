package com.ssk.rule;

import com.ssk.enums.GrayStatusEnum;

/**
 * @description: 请求分发策略，用户可以根据请求特征值进行二开
 * @author: sksun2
 * @create: 2022-06-25
 */
public interface GrayRuleStrategy {

    /**
     *
     * @param request 特征值
     * @return
     */
    GrayStatusEnum request(String request);

}
