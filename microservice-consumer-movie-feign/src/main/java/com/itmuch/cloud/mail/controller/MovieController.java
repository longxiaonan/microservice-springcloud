package com.itmuch.cloud.mail.controller;

import com.itmuch.cloud.mail.feign.UserFeignClient;
import com.itmuch.cloud.mail.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class MovieController {
    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient balancerClient;

    @Autowired
    UserFeignClient userFeignClient;

    /**
     * feign通过自定义的feign client访问user服务节点,通过get方法访问user
     * @param id
     * @return
     */
    @GetMapping("/movie2/{id}")
    public User findByIdFeign(@PathVariable Long id){
        User user = userFeignClient.findById(id);
        return user;
    }

    /** feign通过post方法访问user */
    @GetMapping("/testPost")
    public User testPost(@RequestBody User user){
        return this.userFeignClient.postUser(user);
    }

    /** feign通过get方法访问user测试, (访问包405错误) */
    @GetMapping("/testGet")
    public User testGet(User user){
        return this.userFeignClient.getUser(user);
    }

    /** ribbon通过restTemplate访问user服务节点 */
    @GetMapping("/movie1/{id}")
    public User findByIdRibbon(@PathVariable Long id){
        //通过硬编码的方式
//        return this.restTemplate.getForObject("http://localhost:7900/simple/"+id,User.class);
        //配置ribbon后(@Loadblance),可以通过VIP来访问别的服务:虚拟ip的方式,microservice-provider-user就是虚拟ip
        return this.restTemplate.getForObject("http://microservice-provider-user/simple/"+id,User.class);
    }

    /** 测试ribbon的负载均衡 */
    @GetMapping("/test")
    public String test(){
        //microservice-provider-user使用了自定义的负载均衡方式(随机模式),在主类中进行了定义,会随机获取user的微服务节点
        ServiceInstance serviceInstance = balancerClient.choose("microservice-provider-user");
        System.out.println("111:"+serviceInstance.getServiceId() + ":" +serviceInstance.getHost() + ":" + serviceInstance.getUri() +":"+serviceInstance.getPort());

        //microservice-provider-user02是修改了VIP的user模块,用于模拟另外一个服务节点,使用默认的负载均衡方式(轮询)
//        ServiceInstance serviceInstance02 = balancerClient.choose("microservice-provider-user02");
//        System.out.println("222:"+serviceInstance02.getServiceId() + ":" + serviceInstance02.getHost() + ":" + serviceInstance02.getUri() +":"+serviceInstance02.getPort());
        return null;
    }
}
