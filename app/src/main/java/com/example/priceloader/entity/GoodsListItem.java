package com.example.priceloader.entity;

public class GoodsListItem {

    boolean isSelected;     //是否被选中
    int index;              //在列表中的序号
    String id;
    String name;
    double singlePrice;
    int count;
    double finalPrice;

    public GoodsListItem() {
    }

    public GoodsListItem(int index,String id, String name, double singlePrice) {
        this.index = index;
        this.id = id;
        this.name = name;
        this.singlePrice = singlePrice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
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

    @Override
    public String toString() {
        return "GoodsListItem{" +
                "isSelected=" + isSelected +
                ", index=" + index +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", singlePrice=" + singlePrice +
                ", count=" + count +
                ", finalPrice=" + finalPrice +
                '}';
    }
}
