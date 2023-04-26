package fr.dopolytech.polyshop.gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dopolytech.polyshop.gateway.dtos.ProductAggregate;
import fr.dopolytech.polyshop.gateway.service.ProductAggregatorService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductAggregateController {
  
  @Autowired 
  private ProductAggregatorService service;

  @GetMapping(value = "/{productId}", produces = "application/json")
  public Mono<ResponseEntity<ProductAggregate>> getProductAggregate(@PathVariable long productId) {
    return service.getProductAggregate(productId)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
