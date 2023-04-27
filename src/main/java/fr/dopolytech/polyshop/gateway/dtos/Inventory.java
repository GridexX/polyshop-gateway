package fr.dopolytech.polyshop.gateway.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Inventory {
  public int id;
  public String productId;
  public long quantity;

  public Inventory(long quantity) {
    this.quantity = quantity;
  }
}
