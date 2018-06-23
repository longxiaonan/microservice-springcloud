package com.itmuch.cloud.user.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通过注解@RefreshScope实现属性刷新
 */
@RestController
@RefreshScope
public class UserController {

//    @Value("${profile}")
    private String profile;

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/profile")
    public String profile(){
        System.out.println(profile);
        return profile;
    }

    @GetMapping("getInstance")
    public String test(){
        List<ServiceInstance> list1 = discoveryClient.getInstances("microservice-config-server");
        System.out.println(list1);
        List<ServiceInstance> list2 = discoveryClient.getInstances("microservice-consumer-movie");
        System.out.println(list2);
        return null;
    }
}
