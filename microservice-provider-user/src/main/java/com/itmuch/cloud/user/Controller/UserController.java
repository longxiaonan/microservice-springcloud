package com.itmuch.cloud.user.Controller;

import com.itmuch.cloud.user.dao.UserRepository;
import com.itmuch.cloud.user.entity.User;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/simple/{id}")
    public User findById(@PathVariable Long id){
        return this.userRepository.findById(id).get();
//        return null;
    }

    @PostMapping("/user")
    public User postUser(@RequestBody User user){
        return user;
    }

    @GetMapping("/get-user")
    public User getUser(User user){
        return user;
    }

    @GetMapping("/eureka-isntance")
    public String serviceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("MICROSERVICE-PROVIDER-USER", false);
        return instance.getHomePageUrl();
    }

    @GetMapping("/instance-info")
    public List<String> showInfo(){
        List<String> services = discoveryClient.getServices();
        return services;
    }
}
