package com.itmuch.cloud.authentication;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 这是一个配置类, 注解@EnableConfigurationProperties使刚刚写的类的读取器SecurityProperties生效
 * 其他的core项目相关的配置在该类中进行配置
 * @author longxn
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder PasswordEncoder(){
		//指定加密类,如果要自己自定义加密类,那么自己的加密类实现PasswordEncoder接口
		return new BCryptPasswordEncoder();
	}
}
