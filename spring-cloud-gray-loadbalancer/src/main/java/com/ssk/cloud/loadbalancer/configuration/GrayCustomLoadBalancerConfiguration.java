package com.ssk.cloud.loadbalancer.configuration;

import com.ssk.cloud.loadbalancer.interceptor.GrayRequestInterceptor;
import com.ssk.cloud.loadbalancer.core.GrayServiceInstanceListSupplier;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.cache.LoadBalancerCacheManager;
import org.springframework.cloud.loadbalancer.core.*;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.loadbalancer.support.SimpleObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/17
 */
@LoadBalancerClients(defaultConfiguration = GrayCustomLoadBalancerConfiguration.class)
public class GrayCustomLoadBalancerConfiguration implements ApplicationContextAware , WebMvcConfigurer {

    private ApplicationContext applicationContext;

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private ServiceInstanceListSupplier serviceInstanceListSupplier;

    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            DiscoveryClient discoveryClient, Environment env,
            ApplicationContext context) {

        ServiceInstanceListSupplier delegate = new GrayServiceInstanceListSupplier(
                new DiscoveryClientServiceInstanceListSupplier(discoveryClient, env));

        ObjectProvider<LoadBalancerCacheManager> cacheManagerProvider = context
                .getBeanProvider(LoadBalancerCacheManager.class);
        if (cacheManagerProvider.getIfAvailable() != null) {
            return new CachingServiceInstanceListSupplier(
                    delegate,
                    cacheManagerProvider.getIfAvailable()
            );
        }
        return delegate;
    }

    @Bean
    public ReactorLoadBalancer<ServiceInstance> grayReactorLoadBalancer(
            Environment environment) {
        String isSpringCloudEnabled = applicationContext.getEnvironment().getProperty("autumn.spring.cloud.api.enable");
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

        SimpleObjectProvider<ServiceInstanceListSupplier> provider = new SimpleObjectProvider<>(
                serviceInstanceListSupplier
        );

        return new RandomLoadBalancer(provider, name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GrayRequestInterceptor())
                .addPathPatterns("/**");
    }
}
