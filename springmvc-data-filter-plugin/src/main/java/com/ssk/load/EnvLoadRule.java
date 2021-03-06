package com.ssk.load;


import com.alibaba.fastjson.JSON;
import com.ssk.rule.RulesItem;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 在环境变量中注入解析规则
 *
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
@Slf4j
public class EnvLoadRule implements LoadRule {

    private static final String RULE_FILTER = "RULE_FILTER";

    @Override
    public Map<String,Map<String, List<RulesItem>>> loadRule() {
        String rule_ = System.getenv(RULE_FILTER);
        if(log.isDebugEnabled()){
            log.debug("解析忽的数据:{}", JSON.toJSONString(stringToRuleMap(rule_)));
        }
        return stringToRuleMap(rule_);
    }


    public static void main(String[] args) {
        LoadRule loadRule = new EnvLoadRule();
        Map<String, Map<String, List<RulesItem>>> stringMapMap = loadRule.loadRule();

    }
}
