package com.example.vihu.mobileselection.Models;

import java.util.Date;

public class OrderModel {
    private int orderId;
    private CustomerModel customer;
    private double amount;
    private Date orderDate;
    private BranchModel branch;
    private int loyaltyPoints;

    public OrderModel(int orderId, CustomerModel customer, double amount, Date orderDate, BranchModel branch, int loyaltyPoints) {
        this.orderId = orderId;
        this.customer = customer;
        this.amount = amount;
        this.orderDate = orderDate;
        this.branch = branch;
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BranchModel getBranch() {
        return branch;
    }

    public void setBranch(BranchModel branch) {
        this.branch = branch;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
