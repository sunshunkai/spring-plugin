package io.terminus.gaia.configuration;

import io.terminus.gaia.rule.RulesItem;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/15
 */
@Configuration
@ConfigurationProperties(prefix = "h3c")
@Data
public class RuleConfig {

    /**
     * 脱敏规则
     */
    private Map<String, Map<String, List<RulesItem>>> maps;

    /**
     * 拦截的url
     */
    @Value("${filter.url:/**}")
    private String[] filterUrl;

}
