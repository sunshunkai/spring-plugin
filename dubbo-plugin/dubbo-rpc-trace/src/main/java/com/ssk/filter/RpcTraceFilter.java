package com.ssk.filter;

import com.alibaba.dubbo.common.Constants;
import com.ssk.constant.RpcTraceConstant;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author 惊云
 * @date 2021/12/10 10:09
 */
@Activate(group = {Constants.PROVIDER,Constants.CONSUMER},order = 1)
public class RpcTraceFilter implements Filter {

    /**
     *
     * @param invoker
     * @param invocation
     * @return
     * @throws RpcException
     */
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getContext().getAttachment("trace_id");
        if (StringUtils.isBlank(traceId)) {
            traceId = this.generateTraceId() ;
        }

        //设置日志traceId变量
        MDC.put(RpcTraceConstant.TRACE_ID, traceId);

        RpcContext.getContext().setAttachment(RpcTraceConstant.TRACE_ID, traceId);

        try{
            return invoker.invoke(invocation);
        }finally {
            MDC.remove(RpcTraceConstant.TRACE_ID);
        }
    }

    public String generateTraceId(){
        String traceId = UUID.randomUUID().toString();
        //替换-字符
        return traceId.replaceAll("-", "");
    }
}
