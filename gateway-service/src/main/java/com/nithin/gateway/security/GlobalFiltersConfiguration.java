package com.nithin.gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfiguration {

    private static final Logger LOGGER= LoggerFactory.getLogger(GlobalFiltersConfiguration.class);

    @Order(1)
    @Bean
    public GlobalFilter secondPrefilter(){
        return ((exchange, chain) ->
        {
            LOGGER.info("Mysecond global pre-filter");

            return chain.filter(exchange).then(Mono.fromRunnable(
                    ()->{
                        LOGGER.info("Mysecind global post-filter");
                    }
            ));
        });
    }

    @Order(2)
    @Bean
    public GlobalFilter thirdFilter(){
        return ((exchange, chain) ->
        {
            LOGGER.info("third global pre-filter");

            return chain.filter(exchange).then(Mono.fromRunnable(
                    ()->{
                        LOGGER.info("third global post-filter");
                    }
            ));
        });
    }



}
