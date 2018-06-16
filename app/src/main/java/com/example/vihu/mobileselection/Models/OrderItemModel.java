package com.example.vihu.mobileselection.Models;

public class OrderItemModel {
    private int orderItemId;
    private OrderModel order;
    private ProductModel model;
    private int quantity;

    public OrderItemModel(int orderItemId, OrderModel order, ProductModel model, int quantity) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.model = model;
        this.quantity = quantity;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public ProductModel getModel() {
        return model;
    }

    public void setModel(ProductModel model) {
        this.model = model;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
