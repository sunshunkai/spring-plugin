package io.terminus.gaia.core.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.terminus.gaia.core.json.CommonDesensitizate;
import io.terminus.gaia.rule.RulesItem;

import java.util.List;
import java.util.Map;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/15
 */
public class FastjsonDesensitizate extends CommonDesensitizate {

    public FastjsonDesensitizate(Map<String, Map<String, List<RulesItem>>> rulesItem){
        this.rulesItem = rulesItem;
    }

    @Override
    public Object process(Object object, Class zClass) {

        String a = JSON.toJSONString(object, new FastjsonDesensitizeFilter(rulesItem), SerializerFeature.DisableCircularReferenceDetect);
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
        Object returnObj = JSONObject.parseObject(a,zClass);
        return returnObj;
    }
}
