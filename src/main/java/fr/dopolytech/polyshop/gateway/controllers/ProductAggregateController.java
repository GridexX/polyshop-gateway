package fr.dopolytech.polyshop.gateway.controllers;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.dopolytech.polyshop.gateway.dtos.PostProductDto;
import fr.dopolytech.polyshop.gateway.dtos.Product;
import fr.dopolytech.polyshop.gateway.dtos.ProductAggregate;
import fr.dopolytech.polyshop.gateway.dtos.ProductCart;
import fr.dopolytech.polyshop.gateway.dtos.ProductCartAggregate;
import fr.dopolytech.polyshop.gateway.service.ProductAggregatorService;
import fr.dopolytech.polyshop.gateway.service.ProductClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductAggregateController {

  @Autowired
  private ProductAggregatorService service;

  @GetMapping(value = "/{productId}", produces = "application/json")
  public Mono<ResponseEntity<ProductAggregate>> getProductAggregate(@PathVariable String productId) {
    return service.getProductAggregate(productId)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping(value = "/{productId}", produces = "application/json")
  public Mono<ResponseEntity<ProductAggregate>> putProduct(@PathVariable String productId, @RequestBody PostProductDto postProductDto) {
    return service.putProduct(productId, postProductDto)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping(produces = "application/json")
  @ResponseStatus(code = HttpStatus.CREATED)
  public Mono<ResponseEntity<ProductAggregate>> postProductAggregate(@RequestBody PostProductDto postProductDto) {

    return service.postProductDto(postProductDto)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping
  public Flux<Product> getProducts() {
    return service.getProducts();
  }

  @GetMapping("/cart")
  public Flux<ProductCartAggregate> getProductsCart() {
    return service.getProductsCart();
  }

}
