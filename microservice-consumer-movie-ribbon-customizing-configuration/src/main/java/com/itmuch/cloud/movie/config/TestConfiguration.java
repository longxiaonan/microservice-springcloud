package com.itmuch.cloud.movie.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定制化ribbon
 * 必须配置在@ComponentScan扫描不到的地方(也就是说不能放在主类所在的包和其子包下)
 * 如果非要放在@ComponentScan扫描到的地方,那么使用一个自定义的注解@ExcludeFromComponentScan
 * 再在主类中exclude掉TestConfiguration这个类即可
 * name字段为VIP
 * */
@Configuration
@ExcludeFromComponentScan
public class TestConfiguration {

    @Autowired
    IClientConfig config;

    /** 配置ribbon的负载均衡规则
     * 默认情况下是轮询, 该方法是修改成了随机模式, 是Application主类中对指定的VIP进行指定
     * */
    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new RandomRule();
    }
}