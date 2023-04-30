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
import fr.dopolytech.polyshop.gateway.dtos.Inventory;
import fr.dopolytech.polyshop.gateway.dtos.PutInventoryDto;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(UriConfig.class)
public class InventoryClient {
  private final WebClient client;

  // Define a logger
  private static final Logger logger = LoggerFactory.getLogger(InventoryClient.class);

  public InventoryClient(WebClient.Builder builder) {
    this.client = builder.baseUrl("http://inventory:8082/inventory").build();
  }

  public Mono<Inventory> getInventory(String productId) {
    logger.info("Retrieving inventory for product " + productId);
    Inventory defaultInventory = Inventory.create(productId, 0);

    // Retrieve the inventory for the given product id, if the inventory service is
    // not available, return a default inventory

    return client.get().uri("/{productId}", productId).retrieve().bodyToMono(Inventory.class)
        .onErrorResume(WebClientResponseException.class,
            e -> {
              logger.error("Fail to retrieve inventory WebClient", e);
              return Mono.just(defaultInventory);
            })
        .onErrorResume(Exception.class,
            e -> {
              logger.error("Fail to retrieve inventory ", e);
              return Mono.just(defaultInventory);
            })
        .doOnNext(inv -> logger.info("Inventory retrieved: " + inv.toString()));

  }

  public Mono<Inventory> postInventory(Inventory inventory) {
    logger.info("Posting inventory for product " + inventory.getId());
    return client.post().bodyValue(inventory).retrieve().bodyToMono(Inventory.class)
        .onErrorResume(WebClientResponseException.class,
            e -> {
              logger.error("Fail to post inventory WebClient", e);
              return Mono.just(inventory);
            })
        .onErrorResume(Exception.class,
            e -> {
              logger.error("Fail to post inventory ", e);
              return Mono.just(inventory);
            })
        .doOnNext(inv -> logger.info("Inventory posted: " + inv.toString()));
  }

  public Mono<Inventory> updateInventory(String id, PutInventoryDto putInventoryDto) {
    logger.info("Updating inventory for product " + id);
    return client.put().uri("/{id}", id).bodyValue(putInventoryDto).retrieve().bodyToMono(Inventory.class)
        .onErrorResume(WebClientResponseException.class,
            e -> {
              logger.error("Fail to update inventory WebClient", e);
              return Mono.just(Inventory.create(id, 0));
            })
        .onErrorResume(Exception.class,
            e -> {
              logger.error("Fail to update inventory ", e);
              return Mono.just(Inventory.create(id, 0));
            })
        .doOnNext(inv -> logger.info("Inventory updated: " + inv.toString()));
  }
}
