package com.ssk.router;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.cluster.Router;
import org.apache.dubbo.rpc.cluster.RouterFactory;

/**
 * @description:
 * @author: sksun2
 * @create: 2022-06-25
 */
@Activate(order = 1)
public class GrayRouterFactory implements RouterFactory {

    @Override
    public Router getRouter(URL url) {
        return new GrayRouter();
    }

}
