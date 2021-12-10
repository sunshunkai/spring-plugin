package com.ssk.filter;

import com.alibaba.dubbo.common.Constants;
import com.ssk.constant.RpcTraceConstant;
import com.ssk.spi.GenerateTrace;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

/**
 * @author 惊云
 * @date 2021/12/10 10:09
 */
@Activate(group = {Constants.PROVIDER,Constants.CONSUMER},order = 1)
public class RpcTraceFilter implements Filter {

    private GenerateTrace generateTrace;

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
            traceId = generateTrace.generateTraceId() ;
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

}
