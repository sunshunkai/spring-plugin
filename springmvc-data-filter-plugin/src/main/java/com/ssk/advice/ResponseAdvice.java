package com.ssk.advice;

import com.ssk.load.LoadRule;
import com.ssk.rule.RulesItem;
import com.ssk.context.LogicFlowContext;
import com.ssk.core.json.CommonDesensitizate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
@ControllerAdvice
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice {

    @Autowired
    private LoadRule loadRule;

    @Autowired
    private CommonDesensitizate commonDesensitizate;

    private static Map<String,Map<String, List<RulesItem>>> logicFlowRuleMap;

    /**
     * 拦截的逻辑流
     */
    private static Set<String> logicFlowKeySet;

    @PostConstruct
    public void init() {
        logicFlowRuleMap = loadRule.loadRule();
        logicFlowKeySet = logicFlowRuleMap.keySet();
    }


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 当前请求 logicFlow
        String logicFlow = LogicFlowContext.get();
        return logicFlowKeySet.contains(logicFlow);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Object o = null;
        try{
            o = commonDesensitizate.process(body, returnType.getParameterType());
        }catch (Exception e){
            log.error("序列化失败",e);
        }
        return o == null ? body : o;
    }

}
