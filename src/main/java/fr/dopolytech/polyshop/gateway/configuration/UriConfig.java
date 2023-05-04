package fr.dopolytech.polyshop.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("uri")
public class UriConfig {
    private String cartPath = "/cart";
    private String catalogPath = "/products";
    private String orderPath = "/orders";
    private String inventoryPath = "/inventory";
    private String inventoryCommandPath = "/inventory";

    private String cartUri = "lb://cart-service";
    private String catalogUri = "lb://products-service";
    private String orderUri = "lb://orders-service";
    private String inventoryUri = "lb://inventory-service";
    private String inventoryCommandUri = "lb://inventory-service-command";

    // Define Getter and Setter for those methods

    public String getCartUri() {
        return cartUri;
    }
    
    public String getCatalogUri() {
        return catalogUri;
    }
    public String getOrderUri() {
        return orderUri;
    }
    public String getInventoryUri() {
        return inventoryUri;
    }
    
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

    public String getInventoryCommandPath() {
        return this.inventoryCommandPath;
    }

    public String getInventoryCommandUri() {
        return this.inventoryCommandUri;
    }

}
