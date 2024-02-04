package com.rvs.OrderService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced                   //it will add client side load side balancing..
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

}
