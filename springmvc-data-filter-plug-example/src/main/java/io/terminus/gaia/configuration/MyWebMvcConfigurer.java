package io.terminus.gaia.configuration;

import io.terminus.gaia.conver.PropertiesHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/16
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new PropertiesHttpMessageConverter());
    }

    @Bean
    public PropertiesHttpMessageConverter propertiesHttpMessageConverter(){
        return new PropertiesHttpMessageConverter();
    }

}
