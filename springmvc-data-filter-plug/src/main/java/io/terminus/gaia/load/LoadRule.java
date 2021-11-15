package io.terminus.gaia.load;

import com.alibaba.fastjson.JSONObject;
import io.terminus.gaia.rule.RulesItem;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化脱敏规则
 *
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public interface LoadRule {

    /**
     * 初始化规则解析
     * @return
     */
    Map<String,Map<String, List<RulesItem>>> loadRule();

    /**
     * 将解析好的字符串转成规则对象
     *
     * @param rule_
     * @return
     */
    default Map<String,Map<String, List<RulesItem>>> stringToRuleMap(String rule_){
        if(StringUtils.isEmpty(rule_)){
            return new HashMap<>();
        }
        try {
            return JSONObject.parseObject(rule_, Map.class);
        }catch (Exception e){
            // 字符串解析不了
            return new HashMap<>();
        }

    }

}
