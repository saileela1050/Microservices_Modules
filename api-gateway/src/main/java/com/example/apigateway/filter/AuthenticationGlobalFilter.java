//package com.example.apigateway.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class AuthenticationGlobalFilter implements GlobalFilter, Ordered {
//    private static final String AUTH_HEADER="X-Auth-Token";
//    private static final String SECRET_TOKEN="my-secret";
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String token = exchange.getRequest().getHeaders().getFirst(AUTH_HEADER);
//        if (token ==null || !SECRET_TOKEN.equals(token)) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//
//            return exchange.getResponse().setComplete();
//        }
//        return chain.filter(exchange);
//    }
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
