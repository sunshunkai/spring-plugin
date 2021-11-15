package io.terminus.gaia.core.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import io.terminus.gaia.context.LogicFlowContext;
import io.terminus.gaia.core.json.CommonDesensitizate;
import io.terminus.gaia.rule.DesensiStringUtils;
import io.terminus.gaia.rule.RulesItem;

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
            String writeString = DesensiStringUtils.desensiString(value, ruleListMap.get(fieldName).get(0));
            gen.writeString(writeString);
            return;
        }
        gen.writeString(value);

    }


}
