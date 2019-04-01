package com.fil.Model;

import java.io.Serializable;

public class Product implements Serializable {

    private String price;
    private String name;
    private String description;
    private String image;
//    private String sleeveStyle;
//    private String collarSize;
//    private String collarType;
//    private String material;
//    private String color;

    public Product() {
    }

    public Product(String price, String name, String description, String image){
        this.price = price;
        this.name = name;
        this.description = description;
        this.image = image;
    }

//    public Product(float price, String name, String sleeveStyle, String collarSize, String collarType, String material, String color) {
//        this.price = price;
//        this.name = name;
//        this.sleeveStyle = sleeveStyle;
//        this.collarSize = collarSize;
//        this.collarType = collarType;
//        this.material = material;
//        this.color = color;
//    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    //    public String getSleeveStyle() {
//        return sleeveStyle;
//    }
//
//    public void setSleeveStyle(String sleeveStyle) {
//        this.sleeveStyle = sleeveStyle;
//    }
//
//    public String getCollarSize() {
//        return collarSize;
//    }
//
//    public void setCollarSize(String collarSize) {
//        this.collarSize = collarSize;
//    }
//
//    public String getCollarType() {
//        return collarType;
//    }
//
//    public void setCollarType(String collarType) {
//        this.collarType = collarType;
//    }
//
//    public String getMaterial() {
//        return material;
//    }
//
//    public void setMaterial(String material) {
//        this.material = material;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
}
