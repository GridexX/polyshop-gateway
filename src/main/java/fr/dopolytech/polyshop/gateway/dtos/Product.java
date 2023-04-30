package fr.dopolytech.polyshop.gateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class Product {
  public String id;
	public String name;
	public String description;
	public double price;

	public Product(String id, String name, String description, double price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}
}
