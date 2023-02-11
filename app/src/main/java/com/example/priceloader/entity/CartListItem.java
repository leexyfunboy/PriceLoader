package com.example.priceloader.entity;

public class CartListItem extends ProductItem{

    boolean isSelected;     //是否被选中
    int count;
    double finalPrice;

    public CartListItem() {
    }

    public CartListItem(String id, String name, double inPrice, double singleprice, int totalCount, boolean isSelected, int count, double finalPrice) {
        super(id, name, inPrice, singleprice, totalCount);
        this.isSelected = isSelected;
        this.count = count;
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        return "CartListItem{" +
                "isSelected=" + isSelected +
                ", count=" + count +
                ", finalPrice=" + finalPrice +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", inPrice='" + inPrice + '\'' +
                ", singleprice='" + singleprice + '\'' +
                ", totalCount=" + totalCount +
                '}';
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
