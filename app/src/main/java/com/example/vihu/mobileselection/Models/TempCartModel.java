package com.example.vihu.mobileselection.Models;

public class TempCartModel {
    private String cartId;
    private int quantity;
    private ProductModel model;

    public TempCartModel(String cartId, int quantity, ProductModel model) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.model = model;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductModel getModel() {
        return model;
    }

    public void setModel(ProductModel model) {
        this.model = model;
    }
}
