package com.ssk.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * 用户可以基于SPI机制修改生成规则
 * @author 惊云
 * @date 2021/12/10 10:51
 */
@SPI
public interface GenerateTrace {

    /**
     * 生成分布式 traceId
     * @return
     */
    String generateTraceId();
}
