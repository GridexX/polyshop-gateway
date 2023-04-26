package fr.dopolytech.polyshop.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import fr.dopolytech.polyshop.gateway.configuration.UriConfig;
import fr.dopolytech.polyshop.gateway.dtos.Inventory;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(UriConfig.class)
@EnableDiscoveryClient
public class InventoryClient {
  private final WebClient client;
  
  @Autowired
  private final DiscoveryClient discoveryClient;

  public InventoryClient(WebClient.Builder builder, UriConfig uriConfig, DiscoveryClient discoveryClient) {
    this.discoveryClient = discoveryClient;
    String serviceUrl = this.discoveryClient.getInstances("inventory-service").stream().findFirst().map(s -> s.getUri().toString()).orElse("");
    // Print the service URL
    // System.out.println("Service Inventory URL: " + serviceUrl);
    this.client = builder.baseUrl(serviceUrl+uriConfig.getInventoryPath()+"/").build();
  }

  public Mono<Inventory> getInventory(long productId) {
    return client.get()
      .uri("{productId}", productId)
      .retrieve()
      .bodyToMono(Inventory.class)
      .onErrorResume(ex -> Mono.empty());
  }
}
