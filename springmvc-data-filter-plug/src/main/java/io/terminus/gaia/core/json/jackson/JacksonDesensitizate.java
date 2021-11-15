package io.terminus.gaia.core.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.terminus.gaia.core.json.CommonDesensitizate;
import io.terminus.gaia.rule.RulesItem;
import io.terminus.trantorframework.json.jackjson.OverrideAnySetterBeanDeserializerFactory;
import io.terminus.trantorframework.json.jackjson.config.TrantorModule;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public class JacksonDesensitizate extends CommonDesensitizate {

    /**
     * 这里必须自定义解析器，加入脱敏策略
     */
    private ObjectMapper objectMapper;

    public JacksonDesensitizate(Map<String,Map<String, List<RulesItem>>> rulesItem){
        this.rulesItem = rulesItem;
    }

    @PostConstruct
    public void init() {
        JsonFactory jsonFactory = new SecurityMappingJsonFactory();
        objectMapper = new ObjectMapper(
                jsonFactory, null,
                new DefaultDeserializationContext.Impl(OverrideAnySetterBeanDeserializerFactory.instance))
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .registerModule(new JavaTimeModule())
                .registerModule(new TrantorModule());

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(String.class, new JacksonDesensitizeFilter(rulesItem));
        objectMapper.registerModule(simpleModule);

    }

    @SneakyThrows
    @Override
    public Object process(Object object,Class zClass) {
        String str = objectMapper.writeValueAsString(object);
        return objectMapper.readValue(str, zClass);
    }


}
