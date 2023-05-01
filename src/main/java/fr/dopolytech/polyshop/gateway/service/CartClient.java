package fr.dopolytech.polyshop.gateway.service;

import java.util.ArrayList;

import org.bouncycastle.iana.AEADAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.dopolytech.polyshop.gateway.configuration.UriConfig;
import fr.dopolytech.polyshop.gateway.dtos.ProductCart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(UriConfig.class)
public class CartClient {
  
  private final WebClient client;

  private static Logger logger = LoggerFactory.getLogger(CartClient.class);

  public CartClient(WebClient.Builder builder) {
    this.client = builder.baseUrl("http://cart:8083/cart").build();
  }

  public Flux<ProductCart> getCart() {
    return client.get().retrieve().bodyToFlux(ProductCart.class)
      .onErrorResume(Exception.class,
      e -> {
        logger.error("Fail to get cart ", e);
        return Flux.empty();
      })
      .doOnNext( cart -> {
        logger.info("Cart retrieved");
      });
  }
}
