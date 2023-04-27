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
import fr.dopolytech.polyshop.gateway.dtos.Inventory;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(UriConfig.class)
public class InventoryClient {
  private final WebClient client;

  public InventoryClient(WebClient.Builder builder) {
      this.client = builder.baseUrl("http://inventory:8082/inventory/").build();
  }

  public Mono<Inventory> getInventory(String productId) {
    Inventory defaultInventory = new Inventory(0);
    // Retrieve the inventory for the given product id, if the inventory service is not available, return a default inventory
    return client.get()
    .uri("{productId}", productId)
        .retrieve()
        .bodyToMono(Inventory.class)
        .onErrorResume(ex -> Mono.just(defaultInventory));
  }
}
