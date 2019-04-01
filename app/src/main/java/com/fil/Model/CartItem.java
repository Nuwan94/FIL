package com.fil.Model;

public class CartItem extends Product{

    private String sleeveStyle;
    private String collarSize;
    private String material;
    private String color;
    private String collarType;
    private String tailor;

    public CartItem() {
    }

    public CartItem(Product product, String sleeveStyle, String collarSize, String material, String color, String collarType, String tailor) {
        super(product.getPrice(),product.getName(),product.getDescription(),product.getImage());
        this.sleeveStyle = sleeveStyle;
        this.collarSize = collarSize;
        this.material = material;
        this.color = color;
        this.collarType = collarType;
        this.tailor = tailor;
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

    public String getCollarType() {
        return collarType;
    }

    public void setCollarType(String collarType) {
        this.collarType = collarType;
    }

    public String getTailor() {
        return tailor;
    }

    public void setTailor(String tailor) {
        this.tailor = tailor;
    }
}
