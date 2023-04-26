package fr.dopolytech.polyshop.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("uri")
public class UriConfig {
    private String cartPath = "/cart";
    private String catalogPath = "/products";
    private String orderPath = "/orders";
    private String inventoryPath = "/inventory";
    
    public String getCartPath() {
        return cartPath;
    }

    public void setCartPath(String cartPath) {
        this.cartPath = cartPath;
    }

    public String getCatalogPath() {
        return catalogPath;
    }

    public void setCatalogPath(String catalogPath) {
        this.catalogPath = catalogPath;
    }

    public String getOrderPath() {
        return this.orderPath;
    }

    public void setOrderPath(String orderPath) {
        this.orderPath = orderPath;
    }

    public String getInventoryPath() {
        return this.inventoryPath;
    }

    public void setInventoryPath(String inventoryPath) {
        this.inventoryPath = inventoryPath;
    }

}
