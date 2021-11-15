package io.terminus.gaia.load;



import io.terminus.gaia.rule.RulesItem;
import io.terminus.gaia.utils.FileUtils;

import java.util.List;
import java.util.Map;

/**
 * 通过resource下文件解析规则
 *
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public class ResourceLoadRule implements LoadRule{

    /**
     * 规则文件
     */
    private static final String RULE_FILE = "rule.json";

    @Override
    public Map<String,Map<String, List<RulesItem>>> loadRule() {

        String content = FileUtils.getContentAndSave(RULE_FILE);

        return stringToRuleMap(content);
    }


}
