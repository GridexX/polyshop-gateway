package fr.dopolytech.polyshop.gateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor(staticName = "create")
public class PutInventoryDto {
  long quantity;

  public PutInventoryDto() {
  }
}
