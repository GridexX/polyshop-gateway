package fr.dopolytech.polyshop.gateway.service;

import org.springframework.stereotype.Service;

import fr.dopolytech.polyshop.gateway.dtos.Inventory;
import fr.dopolytech.polyshop.gateway.dtos.Product;
import fr.dopolytech.polyshop.gateway.dtos.ProductAggregate;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@AllArgsConstructor
public class ProductAggregatorService {
  
  private final ProductClient productClient;
  private final InventoryClient inventoryClient;

  public Mono<ProductAggregate> getProductAggregate(Long productId) {
    return Mono.zip(
      this.productClient.getProduct(productId),
      this.inventoryClient.getInventory(productId)
    ).map(this::combine);
  }

  private ProductAggregate combine(Tuple2<Product, Inventory> tuple) {
    Product product = tuple.getT1();
    Inventory inventory = tuple.getT2();
    return ProductAggregate.create(
      product.getId(),
      product.getName(),
      product.getDescription(),
      inventory.getPrice(),
      inventory.getQuantity()
    );
  }
}
