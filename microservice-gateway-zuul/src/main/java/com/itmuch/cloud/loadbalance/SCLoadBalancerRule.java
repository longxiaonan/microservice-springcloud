package com.itmuch.cloud.loadbalance;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

/**
 * @ClassName SCLoadBalancerRule
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/8/16 22:43
 */
public class SCLoadBalancerRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return null;
    }
}
