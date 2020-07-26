package com.itmuch.cloud.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
public class EurekaController {
    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient balancerClient;

    @Autowired
    EurekaInstanceConfigBean eurekaInstanceConfigBean;

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/testEureka")
    public void testEureka(){
        Map<String, String> metadataMap = eurekaInstanceConfigBean.getMetadataMap();

        StringBuffer sb = new StringBuffer();

        metadataMap.forEach((k,v) -> {sb.append(":").append(v);});

        List<ServiceInstance> instances = discoveryClient.getInstances("microservice-provider-user");

        Map<String, String> metadata = instances.get(0).getMetadata();
        System.out.println(instances);
    }



}
