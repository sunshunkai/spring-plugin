package com.ssk.interceptor;

import com.ssk.context.LogicFlowContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
@Component
@Slf4j
public class DataFilterInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LogicFlowContext.remove();
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        LogicFlowContext.set(getLogicFlow(uri));
        return true;
    }

    private String getLogicFlow(String uri){
        String logicFlow = uri.substring(uri.lastIndexOf("_") + 1);
        if(log.isDebugEnabled()){
            log.debug("当前访问逻辑流:{}",logicFlow);
        }
        return logicFlow;
    }



}
