package fr.dopolytech.polyshop.gateway.dtos;

import org.apache.commons.lang.RandomStringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor(staticName = "create")
public class ProductAggregate {
  private String id;
  private String name;
  private String description;
  private double price;
  private long quantity;

  public ProductAggregate(PostProductDto postProductDto) {
    this.id = RandomStringUtils.randomAlphanumeric(24);
    this.name = postProductDto.getName();
    this.description = postProductDto.getDescription();
    this.price = postProductDto.getPrice();
    this.quantity = postProductDto.getQuantity();
  }
}
