package com.ssk.router;

import com.ssk.configuration.GrayConfiguration;
import com.ssk.enums.GrayStatusEnum;
import com.ssk.rule.GrayRuleStrategy;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.router.AbstractRouter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 恢复发布路由
 * @author: sksun2
 * @create: 2022-06-25
 */
public class GrayRouter extends AbstractRouter {

    @Autowired
    private GrayRuleStrategy grayRule;
    @Autowired
    private GrayConfiguration grayConfiguration;


    @Override
    public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        if (CollectionUtils.isEmpty(invokers) || !grayConfiguration.isGrayFlag()) {
            return invokers;
        }
        // 请求灰度路由参数
        String gray = invocation.getAttachment("GRAY_PARAMS");
        GrayStatusEnum request = grayRule.request(gray);
        if(Objects.equals(request,GrayStatusEnum.ALL)){
            return invokers;
        }
        List<Invoker<T>> resultInvoker = invokers.stream()
                .filter(i -> Objects.equals(i.getUrl().getParameter("GRAY_PARAMS"), request.name())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(resultInvoker) && grayConfiguration.isForceProd()){
            return invokers;
        }
        return resultInvoker;
    }
}
