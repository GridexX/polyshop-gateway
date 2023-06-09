package fr.dopolytech.polyshop.gateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor(staticName = "create")
public class Inventory {
  public String id;
  public long quantity;
}
