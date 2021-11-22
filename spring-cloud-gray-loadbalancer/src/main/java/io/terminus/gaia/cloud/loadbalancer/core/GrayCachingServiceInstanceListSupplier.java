package io.terminus.gaia.cloud.loadbalancer.core;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/18
 */
public class GrayCachingServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private static final String PUBLISH_HEADER = "PUBLISH_HEADER";

    private final ServiceInstanceListSupplier delegate;

    public GrayCachingServiceInstanceListSupplier(ServiceInstanceListSupplier delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getServiceId() {
        return delegate.getServiceId();
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return delegate.get();
    }

    @Override
    public Flux<List<ServiceInstance>> get(Request request) {
        return delegate.get();
    }


}
