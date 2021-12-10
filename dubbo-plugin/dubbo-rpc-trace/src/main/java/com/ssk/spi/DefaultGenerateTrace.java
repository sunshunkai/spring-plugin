package com.ssk.spi;

import org.apache.dubbo.common.extension.Activate;

import java.util.UUID;

/**
 * 默认使用 UUID 生成分布式id
 * @author 惊云
 * @date 2021/12/10 10:53
 */
@Activate
public class DefaultGenerateTrace implements GenerateTrace{

    @Override
    public String generateTraceId(){
        String traceId = UUID.randomUUID().toString();
        //替换-字符
        return traceId.replaceAll("-", "");
    }

}
