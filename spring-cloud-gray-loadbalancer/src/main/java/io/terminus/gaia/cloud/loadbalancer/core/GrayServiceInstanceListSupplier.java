package io.terminus.gaia.cloud.loadbalancer.core;

import io.terminus.gaia.cloud.loadbalancer.configuration.GrayConfiguration;
import io.terminus.gaia.cloud.loadbalancer.context.HttpHeaderContext;
import io.terminus.gaia.cloud.loadbalancer.context.PublishType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 灰度请求
 *
 * @author 孙顺凯（惊云）
 * @date 2021/11/17
 */
public class GrayServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private static final String PUBLISH_HEADER = "publish-header";

    private final ServiceInstanceListSupplier delegate;

    @Autowired
    private GrayConfiguration grayConfiguration;

    public GrayServiceInstanceListSupplier(ServiceInstanceListSupplier delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getServiceId() {
        return delegate.getServiceId();
    }


    @Override
    public Flux<List<ServiceInstance>> get() {
        return delegate.get().map(info->filteredGrayService(info));
    }

    @Override
    public Flux<List<ServiceInstance>> get(Request request) {
        return delegate.get().map(info->filteredGrayService(info));
    }

    private List<ServiceInstance> filteredGrayService(List<ServiceInstance> serviceInstances) {
        if( CollectionUtils.isEmpty(serviceInstances) ){
            return serviceInstances;
        }

        Boolean grayHeader = grayConfiguration.getGrayHeader();
        if( !grayHeader || !PublishType.GRAY.name().equals(getGrayHeader())){
            return serviceInstances;
        }
        List<ServiceInstance> greyServiceInstanceList = serviceInstances.stream()
                .filter(service -> service.getMetadata() != null && PublishType.GRAY.name()
                                                        .equals(service.getMetadata().get(PUBLISH_HEADER)))
                .collect(Collectors.toList());
        if( CollectionUtils.isEmpty(greyServiceInstanceList) ){
            // 没有找到灰度服务
            return serviceInstances;
        }
        return greyServiceInstanceList;
    }


    /**
     * 从请求头里面获取是否需要灰度请求
     * @return
     */
    private String getGrayHeader(){
        return HttpHeaderContext.get();
    }


}
