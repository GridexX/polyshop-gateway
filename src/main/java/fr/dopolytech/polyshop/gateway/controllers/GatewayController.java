package fr.dopolytech.polyshop.gateway.controllers;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import fr.dopolytech.polyshop.gateway.configuration.UriConfig;

@CrossOrigin(origins = "*")
@EnableConfigurationProperties(UriConfig.class)
@RestController
public class GatewayController {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfig uriConfig) {
        return builder.routes()
                .route(p -> p
                        .path(uriConfig.getCartPath() + "/**")
                        .uri(uriConfig.getCartUri()))
                .route(p -> p
                        .path(uriConfig.getCatalogPath() + "/**")
                        .and().method(HttpMethod.GET)
                        .uri(uriConfig.getCatalogUri()))
                .route(p -> p
                        .path(uriConfig.getOrderPath() + "/**")
                        .uri(uriConfig.getOrderUri()))
                .route(p -> p
                        .path(uriConfig.getInventoryPath() + "/**")
                        .and().method(HttpMethod.GET)
                        .uri(uriConfig.getInventoryUri()))
                .route(p -> p
                        .path(uriConfig.getInventoryCommandPath() + "/**")
                        .and().method(HttpMethod.PUT)
                        .and().method(HttpMethod.POST)
                        .and().method(HttpMethod.DELETE)
                        .uri(uriConfig.getInventoryCommandUri()))

                .build();
    }
}
