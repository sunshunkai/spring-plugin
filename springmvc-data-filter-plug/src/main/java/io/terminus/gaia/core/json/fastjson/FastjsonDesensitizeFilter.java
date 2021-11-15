package io.terminus.gaia.core.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import io.terminus.gaia.context.LogicFlowContext;
import io.terminus.gaia.rule.DesensiStringUtils;
import io.terminus.gaia.rule.RulesItem;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public class FastjsonDesensitizeFilter implements ValueFilter {

    private Map<String,Map<String,List<RulesItem>>> rulesItemList;

    public FastjsonDesensitizeFilter(Map<String,Map<String,List<RulesItem>>> rulesItemList){
        this.rulesItemList = rulesItemList;
    }

    @SneakyThrows
    @Override
    public Object process(Object object, String name, Object value) {

        String logicFlow = LogicFlowContext.get();
        Map<String, List<RulesItem>> stringListMap = rulesItemList.get(logicFlow);

        if( null == value || !stringListMap.containsKey(name) ){
            return value;
        }
        RulesItem rules = JSONObject.parseObject(JSON.toJSONString(stringListMap.get(name).get(0)), RulesItem.class);
        return DesensiStringUtils.desensiString(value.toString(),rules);
    }

}
