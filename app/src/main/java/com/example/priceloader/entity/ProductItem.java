package com.example.priceloader.entity;

public class ProductItem implements Product{

    public String id;
    public String name;
    public double inPrice;
    public double singleprice;
    public int totalCount;

    public ProductItem() {
    }

    public ProductItem(String id, String name, double inPrice, double singleprice, int totalCount) {
        this.id = id;
        this.name = name;
        this.inPrice = inPrice;
        this.singleprice = singleprice;
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", inPrice='" + inPrice + '\'' +
                ", singleprice='" + singleprice + '\'' +
                ", totalCount=" + totalCount +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInPrice() {
        return inPrice;
    }

    public void setInPrice(double inPrice) {
        this.inPrice = inPrice;
    }

    public double getSingleprice() {
        return singleprice;
    }

    public void setSingleprice(double singleprice) {
        this.singleprice = singleprice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
