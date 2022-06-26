package com.ssk.rule;

import com.ssk.enums.GrayStatusEnum;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-06-25
 */
public class DefaultGrayRuleStrategy implements GrayRuleStrategy {

    @Override
    public GrayStatusEnum request(String request) {
        return GrayStatusEnum.ALL;
    }
}
