package io.terminus.gaia.cloud.loadbalancer.interceptor;

import io.terminus.gaia.cloud.loadbalancer.context.HttpHeaderContext;
import io.terminus.trantorframework.globalreq.context.GlobalReqContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/18
 */
@Slf4j
@Component
public class GrayRequestInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HttpHeaderContext.remove();
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpHeaderContext.set(getGrayHeader());
        return true;
    }

    private String getGrayHeader(){

        String publishHeader = GlobalReqContext.getData("publish-header", String.class);

        if( Objects.isNull(publishHeader) ){
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            publishHeader =  request.getHeader("publish-header");
            // 如果是 Trantor 发起的RPC,则只能从 Trantor 获取 Header
            GlobalReqContext.addData("publish-header",publishHeader);
        }
        return publishHeader;
    }
}
