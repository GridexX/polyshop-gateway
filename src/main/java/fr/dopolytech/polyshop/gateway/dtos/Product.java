package fr.dopolytech.polyshop.gateway.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {
  private long id;
  private String name;
  private String description;
}
