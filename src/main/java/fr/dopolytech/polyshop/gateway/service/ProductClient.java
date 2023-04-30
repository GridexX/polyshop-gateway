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
import org.springframework.web.reactive.function.client.WebClientResponseException;

import fr.dopolytech.polyshop.gateway.configuration.UriConfig;
import fr.dopolytech.polyshop.gateway.dtos.PostProductDto;
import fr.dopolytech.polyshop.gateway.dtos.Product;
import jakarta.annotation.PreDestroy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(UriConfig.class)
public class ProductClient {

  private final WebClient client;

  // Define a logger
  private static final Logger logger = LoggerFactory.getLogger(ProductClient.class);

  public ProductClient(WebClient.Builder builder) {
    this.client = builder.baseUrl("http://catalog:8081/products").build();
  }

  public Mono<Product> getProduct(String productId) {
    return client.get()
        .uri("/{productId}", productId)
        .retrieve()
        .bodyToMono(Product.class).onErrorResume(ex -> Mono.empty());
  }

  public Mono<Product> postProduct(Product product) {
    logger.info("Post product " + product.getId());
    return client.post().bodyValue(product).retrieve().bodyToMono(Product.class)
        .onErrorResume(WebClientResponseException.class,
            e -> {
              logger.error("Fail to post product WebClient", e);
              return Mono.just(product);
            })
        .onErrorResume(Exception.class,
            e -> {
              logger.error("Fail to post product ", e);
              return Mono.just(product);
            })
        .doOnNext(inv -> logger.info("Product posted: " + inv.toString()));
  }

  public Flux<Product> getProducts() {
    // The method return in the body an array of products
    return client.get()
        .retrieve()
        .bodyToFlux(Product.class)
        .onErrorResume(ex -> Flux.empty());
  }

  public Mono<Product> updateProduct(String productId, PostProductDto product) {
    logger.info("Updating product " + productId);

    Product productToUpdate = new Product(productId, product.getName(), product.getDescription(), product.getPrice());
    
    return client.put().uri("/{productId}", productId).bodyValue(product).retrieve().bodyToMono(Product.class)
        .onErrorResume(WebClientResponseException.class,
            e -> {
              logger.error("Fail to update product WebClient", e);
              return Mono.just(productToUpdate);
            })
        .onErrorResume(Exception.class,
            e -> {
              logger.error("Fail to update product ", e);
              return Mono.just(productToUpdate);
            })
        .doOnNext(inv -> logger.info("Product updated: " + inv.toString()));
  }
}
