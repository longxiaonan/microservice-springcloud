package com.itmuch.cloud.movie.controller;

import com.itmuch.cloud.movie.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MovieController {
    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient balancerClient;

    @GetMapping("/movie/{id}")
    @HystrixCommand(fallbackMethod = "findByIdFollback")
    public User findById(@PathVariable Long id){
        //通过硬编码的方式
//        return this.restTemplate.getForObject("http://localhost:7900/simple/"+id,User.class);
        //配置ribbon后(@Loadblance),可以通过VIP来访问别的服务:虚拟ip的方式,microservice-provider-user就是虚拟ip
        return this.restTemplate.getForObject("http://microservice-provider-user/simple/"+id,User.class);
    }

    /** 浏览器访问/movie/1 请求findById方法, 当User服务无法访问时, 会访问该方法 */
    public User findByIdFollback(Long id){
        User user = new User();
        user.setId(5L);
        user.setName("longxn");
        return user;
    }




    /** 使用ribbon的API来测试ribbon的负载均衡 */
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
