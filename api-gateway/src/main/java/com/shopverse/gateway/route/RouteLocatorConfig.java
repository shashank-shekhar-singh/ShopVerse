package com.shopverse.gateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.shopverse.gateway.route.RouteConstants.AUTH_API;
import static com.shopverse.gateway.route.RouteConstants.AUTH_SERVICE;
import static com.shopverse.gateway.route.RouteConstants.USER_API;
import static com.shopverse.gateway.route.RouteConstants.USER_SERVICE;

@Configuration
public class RouteLocatorConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", route -> route
                        .path(AUTH_API)
                        .uri("lb://" + AUTH_SERVICE))
                .route("user-service", route -> route
                        .path(USER_API)
                        .uri("lb://" + USER_SERVICE))
                .build();
    }
}
