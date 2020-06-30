package com.edu.neu.healthlung.entity;

public class LungNode {
    private LungNodeType level;
    private Double length;
    private Double width;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public LungNodeType getLevel() {
        return level;
    }

    public void setLevel(LungNodeType level) {
        this.level = level;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public enum LungNodeType{
        磨玻璃密度, 实性, 部分实性
    }
}
