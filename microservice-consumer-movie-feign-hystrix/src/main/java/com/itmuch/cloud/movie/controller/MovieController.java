package com.itmuch.cloud.movie.controller;

import com.itmuch.cloud.movie.feign.UserFeignClient;
import com.itmuch.cloud.movie.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import java.util.Map;

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
        User user = null;
        try {
            user = userFeignClient.findById(id);
        }catch (ConstraintViolationException e){//javax.validation.ConstraintViolationException: findById.arg0: 最小不能小于3
            System.err.println(e.getStackTrace());
        }
        return user;
    }

    /** feign通过post方法访问user */
    //GET:http://localhost:7901/testPost?id=1&username=zhansan
    @GetMapping("/testPost")    //支持不加@RequestParam的复杂类型的User
//    @RequestMapping(method = RequestMethod.GET, value="/testPost")   //支持不加@RequestParam的复杂类型的User
    public User testPost(@ModelAttribute User user){   //支持加或不加@RequestParam的基本类型的User, 加@ModelAttribute或者不加都可以
        return this.userFeignClient.postUser(user);
    }

    //POST:http://localhost:7901/testPost1?id=1&username=zhansan  //url中的参数直接注入到了对象中
    @PostMapping("/testPost1")  //支持不加@RequestParam的复杂类型的User(只接收url中参数)
    public User testPost1(User user){
        return this.userFeignClient.postUser(user);
    }

    //http://localhost:7901/testPost2?id=1&username=zhansan&aa=11111  //url中的参数无法通过@RequestBody获取
    @PostMapping("/testPost2")  //支持加@RequestBody的复杂类型的User,通过在body({"username":"2","age":1})通过json格式(Content-Type:application/json)
                                //user对象封装body中的参数, user2对象封装url中的参数, aa封装字符串"11111"
    public User testPost2(@RequestBody User user, User user2, String aa){
        return this.userFeignClient.postUser(user);
    }

    //POST:http://localhost:7901/testPost3?username=11&username=22   //url中的参数无法通过@RequestBody获取
    @PostMapping("/testPost3")  //支持加@RequestBody的复杂类型的User,通过在body({"username":"2","age":1})通过json格式(Content-Type:application/json)
    public User testPost3(@RequestParam String[] username){
        User user1 = new User();
        user1.setId(33L);
        user1.setName("longxn3333");
        return user1;
    }

    //POST:http://localhost:7901/testPost1?id=1&username=zhansan   //url中的参数无法通过@RequestBody获取
    @PutMapping("/testPut1")  //支持加@RequestBody的复杂类型的User,通过在body({"username":"2","age":1})通过json格式(Content-Type:application/json)
    public User testPut1(@RequestBody User user){
        User user1 = new User();
        user1.setId(33L);
        user1.setName("longxn3333");
        return user1;
    }

    //POST:http://localhost:7901/testPost1?id=1&username=zhansan   //url中的参数无法通过@RequestBody获取
    @DeleteMapping("/testDelete")  //支持加@RequestBody的复杂类型的User,通过在body({"username":"2","age":1})通过json格式(Content-Type:application/json)
    public User testDelete(User user2, String aa){
        return this.userFeignClient.postUser(user2);
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
