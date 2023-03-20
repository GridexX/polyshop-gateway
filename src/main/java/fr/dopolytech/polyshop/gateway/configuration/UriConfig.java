package fr.dopolytech.polyshop.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("uri")
public class UriConfig {
    private String cartUri = "http://localhost:8090";
    private String catalogUri = "http://localhost:8092";
    private String orderUri = "http://localhost:8093";

    private String cartPath = "/cart";
    private String catalogPath = "/products";
    private String orderPath = "/orders";

    public String getCartUri() {
        return cartUri;
    }

    public void setCartUri(String cartUri) {
        this.cartUri = cartUri;
    }

    public String getCatalogUri() {
        return catalogUri;
    }

    public void setCatalogUri(String catalogUri) {
        this.catalogUri = catalogUri;
    }

    public String getOrderUri() {
        return this.orderUri;
    }

    public void setOrderUri(String orderUri) {
        this.orderUri = orderUri;
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

}
