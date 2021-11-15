package io.terminus.gaia.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.org.apache.bcel.internal.ExceptionConst;
import io.terminus.gaia.context.LogicFlowContext;
import io.terminus.gaia.core.json.CommonDesensitizate;
import io.terminus.gaia.core.json.fastjson.FastjsonDesensitizeFilter;
import io.terminus.gaia.load.LoadRule;
import io.terminus.gaia.rule.JSONUtils;
import io.terminus.gaia.rule.RulesItem;
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
        boolean support = logicFlowKeySet.contains(logicFlow);
        if(log.isDebugEnabled()){
            log.debug("脱敏集合:{},当前访问是否需要脱敏,{},当前请求:{}",logicFlowKeySet,support,logicFlow);
        }
        return support;
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
