package com.ssk.conver;

import com.alibaba.fastjson.support.spring.messaging.MappingFastJsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/16
 */

public class PropertiesHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    @Autowired
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

//    @Autowired
//    private MappingFastJsonMessageConverter mappingFastJsonMessageConverter;

    @Override
    protected boolean supports(Class clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return mappingJackson2HttpMessageConverter.read(clazz,inputMessage);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        mappingJackson2HttpMessageConverter.write(o,new MediaType("application", "*+json"),outputMessage);
    }
}
