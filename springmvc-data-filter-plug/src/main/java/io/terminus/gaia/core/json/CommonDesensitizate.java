package io.terminus.gaia.core.json;

import io.terminus.gaia.rule.RulesItem;

import java.util.List;
import java.util.Map;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public abstract class CommonDesensitizate implements Desensitizate{

    /**
     * 脱敏规则
     */
    protected Map<String,Map<String, List<RulesItem>>> rulesItem;


}
