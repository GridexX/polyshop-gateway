package fr.dopolytech.polyshop.gateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor(staticName = "create")
public class PostProductDto {
  private String name;
  private String description;
  private double price;
  private long quantity;
}
