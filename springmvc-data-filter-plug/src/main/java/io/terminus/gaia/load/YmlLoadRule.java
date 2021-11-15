package io.terminus.gaia.load;

import io.terminus.gaia.configuration.RuleConfig;
import io.terminus.gaia.rule.RulesItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/** 
 * 通过 application.yml 加载规则文件
 * @author 孙顺凯（惊云）
 * @date 2021/11/15
 */
public class YmlLoadRule implements LoadRule{

    @Autowired(required = false)
    private RuleConfig ruleConfig;

    @Override
    public Map<String, Map<String, List<RulesItem>>> loadRule() {
        return ruleConfig.getMaps();
    }
}
