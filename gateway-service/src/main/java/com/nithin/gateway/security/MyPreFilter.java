//package com.nithin.gateway.security;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Set;
//
//@Component
//public class MyPreFilter implements GlobalFilter, Ordered {
//
//    public static final Logger LOGGER= LoggerFactory.getLogger(MyPreFilter.class);
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        LOGGER.info("Pre FIlter..");
//        String requestPath=exchange.getRequest().getPath().toString();
//        LOGGER.info("The Requested Path is {}",requestPath);
//
//        HttpHeaders headers= exchange.getRequest().getHeaders();
//        Set<String>headerNames=headers.keySet();
//      headerNames.forEach((headerName)->{
//            String headervalue=headers.getFirst(headerName);
//            LOGGER.info("The headersName-{} and HeaderValue-{}",headerName,headervalue);
//        });
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
