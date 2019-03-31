package com.fil.Model;

public class Product {

    private float price;
    private String name;
    private String sleeveStyle;
    private String collarSize;
    private String collarType;
    private String material;
    private String color;

    public Product() {
    }

    public Product(float price, String name, String sleeveStyle, String collarSize, String collarType, String material, String color) {
        this.price = price;
        this.name = name;
        this.sleeveStyle = sleeveStyle;
        this.collarSize = collarSize;
        this.collarType = collarType;
        this.material = material;
        this.color = color;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSleeveStyle() {
        return sleeveStyle;
    }

    public void setSleeveStyle(String sleeveStyle) {
        this.sleeveStyle = sleeveStyle;
    }

    public String getCollarSize() {
        return collarSize;
    }

    public void setCollarSize(String collarSize) {
        this.collarSize = collarSize;
    }

    public String getCollarType() {
        return collarType;
    }

    public void setCollarType(String collarType) {
        this.collarType = collarType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
