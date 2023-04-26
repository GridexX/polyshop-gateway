package fr.dopolytech.polyshop.gateway.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Inventory {
  private String id;
  private double price;
  private int quantity;
}
