package com.itmuch.cloud.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserProps
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/8/10 18:02
 */
@ConfigurationProperties(prefix = "user")
@Component
//@RefreshScope
public class UserProps {
    String name;
    Integer age;

    //类上不加@RefreshScope不刷新
    @Value("${profile}")
    String profile;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
