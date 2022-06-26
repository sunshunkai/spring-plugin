package com.ssk.configuration;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-06-25
 */
@Data
@Configuration
public class GrayConfiguration {

    /**
     * 请求头参数中灰度参数名
     */
    private String grayParamsName;

    /**
     * 是否开启灰度功能
     */
    private boolean grayFlag;

    /**
     * 在没有灰度机器时强制使用生产机器
     */
    private boolean forceProd;

}
