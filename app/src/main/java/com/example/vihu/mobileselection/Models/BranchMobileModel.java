package com.example.vihu.mobileselection.Models;

public class BranchMobileModel {
    private int branchMobileId;
    private BranchModel branch;
    private ProductModel model;
    private int quantity;

    public BranchMobileModel(int branchMobileId, BranchModel branch, ProductModel model, int quantity) {
        this.branchMobileId = branchMobileId;
        this.branch = branch;
        this.model = model;
        this.quantity = quantity;
    }

    public int getBranchMobileId() {
        return branchMobileId;
    }

    public void setBranchMobileId(int branchMobileId) {
        this.branchMobileId = branchMobileId;
    }

    public BranchModel getBranch() {
        return branch;
    }

    public void setBranch(BranchModel branch) {
        this.branch = branch;
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
