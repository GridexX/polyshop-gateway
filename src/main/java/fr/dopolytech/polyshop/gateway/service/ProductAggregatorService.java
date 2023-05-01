package fr.dopolytech.polyshop.gateway.service;

import org.springframework.stereotype.Service;

import fr.dopolytech.polyshop.gateway.dtos.Inventory;
import fr.dopolytech.polyshop.gateway.dtos.PostProductDto;
import fr.dopolytech.polyshop.gateway.dtos.Product;
import fr.dopolytech.polyshop.gateway.dtos.ProductAggregate;
import fr.dopolytech.polyshop.gateway.dtos.ProductCart;
import fr.dopolytech.polyshop.gateway.dtos.ProductCartAggregate;
import fr.dopolytech.polyshop.gateway.dtos.PutInventoryDto;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@AllArgsConstructor
public class ProductAggregatorService {

  private final ProductClient productClient;
  private final InventoryClient inventoryClient;
  private final CartClient cartClient;

  public Mono<ProductAggregate> getProductAggregate(String productId) {
    return Mono.zip(
        this.productClient.getProduct(productId),
        this.inventoryClient.getInventory(productId)).map(this::combine);
  }

  private ProductAggregate combine(Tuple2<Product, Inventory> tuple) {
    Product product = tuple.getT1();
    Inventory inventory = tuple.getT2();
    return ProductAggregate.create(
        inventory.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        inventory.getQuantity());
  }

  public Mono<ProductAggregate> postProductDto(PostProductDto postProductDto) {

    ProductAggregate productAggregate = new ProductAggregate(postProductDto);

    return Mono.zip(
        this.productClient.postProduct(
            new Product(
                productAggregate.getId(),
                productAggregate.getName(),
                productAggregate.getDescription(),
                productAggregate.getPrice())),
        this.inventoryClient.postInventory(
            Inventory.create(
                productAggregate.getId(),
                productAggregate.getQuantity())))
        .map(this::combine);
  }

  public Flux<Product> getProducts() {
    return this.productClient.getProducts();
  }

  public Mono<ProductAggregate> putProduct(String productId, PostProductDto postProductDto) {

    ProductAggregate productAggregate = ProductAggregate.create(productId, postProductDto.getName(),
        postProductDto.getDescription(), postProductDto.getPrice(), postProductDto.getQuantity());

    return Mono.zip(
        this.productClient.updateProduct(productId, postProductDto),
        this.inventoryClient.updateInventory(productId, PutInventoryDto.create(
            productAggregate.getQuantity())))
        .map(this::combine);

  }

  public Flux<ProductCartAggregate> getProductsCart() {

    Flux<ProductCart> productCartFlux = cartClient.getCart();

    return productCartFlux.flatMap(productCart -> {
      Mono<Product> productMono = productClient.getProduct(productCart.getId());
      return Mono.zip(productMono, Mono.just(productCart))
          .map(tuple -> ProductCartAggregate.create(tuple.getT1().id, tuple.getT1().name, tuple.getT1().description,
              tuple.getT1().price, productCart.getAmount()));
    });

  }
}
