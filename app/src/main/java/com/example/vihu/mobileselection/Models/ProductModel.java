package com.example.vihu.mobileselection.Models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private int modelId;
    private String modelName;
    private BrandModel brand;
    private int make;
    private double price;
    private String processor;
    private String camera;
    private String ram;
    private String storage;

    public ProductModel(int modelId, String modelName, BrandModel brand, int make, double price,
                        String processor, String camera, String ram, String storage) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.brand = brand;
        this.make = make;
        this.price = price;
        this.processor = processor;
        this.camera = camera;
        this.ram = ram;
        this.storage = storage;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public BrandModel getBrand() {
        return brand;
    }

    public void setBrand(BrandModel brand) {
        this.brand = brand;
    }

    public int getMake() {
        return make;
    }

    public void setMake(int make) {
        this.make = make;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}
