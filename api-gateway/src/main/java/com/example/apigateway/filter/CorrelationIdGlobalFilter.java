//package com.example.apigateway.filter;
//
//import org.springframework.beans.factory.parsing.EmptyReaderEventListener;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.UUID;
//
//import static reactor.netty.http.HttpConnectionLiveness.log;
//
//
//@Component
//public class CorrelationIdGlobalFilter implements GlobalFilter, Ordered {
//    private static final String CORRELATION_ID="X-Correlation-Id";
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String correlationId=exchange.getRequest().getHeaders().getFirst(CORRELATION_ID);
//        log.info("correlation id",correlationId);
//        if(correlationId==null || correlationId.isEmpty()){
//            correlationId= UUID.randomUUID().toString();
//        }
//        ServerHttpRequest request=exchange.getRequest().mutate().header(CORRELATION_ID,correlationId)
//                .build();
//        ServerWebExchange modifiedExchange=exchange.mutate().request(request).build();
//        return chain.filter(modifiedExchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//}
package com.example.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdGlobalFilter implements GlobalFilter, Ordered {

    private static final String REQUEST_ID = "X-Request-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestId = exchange.getRequest()
                .getHeaders()
                .getFirst(REQUEST_ID);

        if (requestId == null || requestId.isBlank()) {

            requestId = UUID.randomUUID().toString();
        }

        ServerWebExchange modifiedExchange =
                exchange.mutate()
                        .request(
                                exchange.getRequest()
                                        .mutate()
                                        .header(REQUEST_ID, requestId)
                                        .build()
                        )
                        .build();

        return chain.filter(modifiedExchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
