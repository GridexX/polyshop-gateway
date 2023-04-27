package fr.dopolytech.polyshop.gateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.dopolytech.polyshop.gateway.configuration.UriConfig;
import fr.dopolytech.polyshop.gateway.dtos.Product;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(UriConfig.class)
public class ProductClient {

  private final WebClient client;

  public ProductClient(WebClient.Builder builder) {
      this.client = builder.baseUrl("http://catalog:8081/products/").build();
  }

  public Mono<Product> getProduct(String productId) {
    return client.get()
    .uri("{productId}", productId)
        .retrieve()
        .bodyToMono(Product.class)
        .onErrorResume(ex -> Mono.empty());
  }
}
