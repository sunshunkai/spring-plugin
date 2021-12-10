package com.ssk.cloud.loadbalancer.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/18
 */
@Slf4j
@Component
public class HeaderFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).subscriberContext(ctx -> {
            //注意这里一定要return ctx.put。put操作是产生一个新的context。如果是return ctx;会导致值没有设置进去
            return  ctx.put("token", "xx");
        });
    }

}
