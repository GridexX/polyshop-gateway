package fr.dopolytech.polyshop.gateway.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductCart {
  String id;
  long amount;  
}
