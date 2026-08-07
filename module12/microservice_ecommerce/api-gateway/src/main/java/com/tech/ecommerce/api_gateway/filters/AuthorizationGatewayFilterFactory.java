package com.tech.ecommerce.api_gateway.filters;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizationGatewayFilterFactory.Config> {

    public AuthorizationGatewayFilterFactory(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

                log.info("Authorization Filter Called");

                // agar filter disable hai
                if (!config.isEnabled()) {
                    return chain.filter(exchange);
                }
                String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                if(authorizationHeader == null  || !authorizationHeader.startsWith("Bearer ")){
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                // token nikal lo
                String token = authorizationHeader.split("Bearer ")[1];

                log.info("Token Received: {}", token);

                // yahan JWT validation logic likh sakte ho

                return chain.filter(exchange);
            }
        };
    }

    @Data
    public static class Config{
        private boolean isEnabled = true;
        private String allowedRoles;
    }

}
