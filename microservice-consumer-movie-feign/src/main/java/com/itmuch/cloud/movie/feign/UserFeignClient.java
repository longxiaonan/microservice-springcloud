package com.itmuch.cloud.movie.feign;

import com.itmuch.cloud.movie.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("microservice-provider-user")
public interface UserFeignClient {

    /**
     * 测试get方法
     * 两个坑, 1.不支持GetMapping, 2.@PathVariable必须别名
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/simple/{id}")
    public User findById(@PathVariable("id") Long id);

    /**
     * 测试post方法,貌似不管user服务的"/user"是post还是get方法, 这里的method设置成post和get都可以
     *
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public User postUser(@RequestBody User user);

    /**
     * 改方法访问user不会成功,报错405,因为参数是复杂对象,会通过post方式发生数据(user服务的/get-user是限定了是get方法),而不是method指定的方法
     * 如果需要那么通过@RequestParam("xx")一个个传参
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get-user")
//    public User getUser(User user);
    public User getUser(@RequestBody User user); //同上
}
