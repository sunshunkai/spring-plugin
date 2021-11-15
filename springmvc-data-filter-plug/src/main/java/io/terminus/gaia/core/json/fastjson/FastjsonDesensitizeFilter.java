package io.terminus.gaia.core.json.fastjson;

import com.alibaba.fastjson.serializer.ValueFilter;
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

    private Map<String,List<RulesItem>> rulesItem;

    public FastjsonDesensitizeFilter(Map<String,List<RulesItem>> rulesItem){
        this.rulesItem = rulesItem;
    }

    @SneakyThrows
    @Override
    public Object process(Object object, String name, Object value) {

        if( null == value || !rulesItem.containsKey(name) ){
            return value;
        }
        return DesensiStringUtils.desensiString(value.toString(),rulesItem.get(name).get(0));
    }

}
