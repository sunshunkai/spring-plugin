package com.ssk.service.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.ssk.context.LogicFlowContext;
import com.ssk.rule.RulesItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public class JacksonDesensitizeFilter extends JsonSerializer<String> {

    private Map<String,Map<String, List<RulesItem>>> rulesItem;

    public JacksonDesensitizeFilter(){
        rulesItem = new HashMap<>();
    }

    public JacksonDesensitizeFilter(Map<String,Map<String, List<RulesItem>>> rulesItem){
        this.rulesItem = rulesItem;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String logicFlow = LogicFlowContext.get();
        SecurityWriterBasedJsonGenerator newGen = (SecurityWriterBasedJsonGenerator) gen;
        String fieldName = newGen.getFieldName();

        // 当前逻辑流的规则
        Map<String, List<RulesItem>> ruleListMap = rulesItem.get(logicFlow);

        if( ruleListMap != null && ruleListMap.containsKey(fieldName) ){
//            RulesItem rulesItem = JSONObject.parseObject(JSON.toJSONString(ruleListMap.get(fieldName).get(0)), RulesItem.class);
//            String writeString = DesensiStringUtils.desensiString(value, rulesItem);
            gen.writeString("*******");
            return;
        }
        gen.writeString(value);

    }


}
