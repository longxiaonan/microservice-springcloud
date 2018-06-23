package com.itmuch.cloud.movie;

import com.itmuch.cloud.movie.config.ExcludeFromComponentScan;
import com.itmuch.cloud.movie.config.TestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@RibbonClient(name = "microservice-provider-user", configuration = TestConfiguration.class)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,value = ExcludeFromComponentScan.class))
public class MovieRibbonHardcodeApplication {

    @Bean
    @LoadBalanced //开启ribbon
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(MovieRibbonHardcodeApplication.class, args);
    }
}
