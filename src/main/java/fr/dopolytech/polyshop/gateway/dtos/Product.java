package fr.dopolytech.polyshop.gateway.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {
  public String id;
	public String productId;
	public String name;
	public String description;
	public double price;
}
