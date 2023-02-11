package com.example.priceloader.entity;

public class Product_detail {

    String p_barcode;
    String p_name;
    double single_in_price;
    double single_sell_price;
    int storageCount;

    @Override
    public String toString() {
        return "Product_detail{" +
                "p_barcode='" + p_barcode + '\'' +
                ", p_name='" + p_name + '\'' +
                ", single_in_price=" + single_in_price +
                ", single_sell_price=" + single_sell_price +
                ", storageCount=" + storageCount +
                '}';
    }

    public String getP_barcode() {
        return p_barcode;
    }

    public void setP_barcode(String p_barcode) {
        this.p_barcode = p_barcode;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public double getSingle_in_price() {
        return single_in_price;
    }

    public void setSingle_in_price(double single_in_price) {
        this.single_in_price = single_in_price;
    }

    public double getSingle_sell_price() {
        return single_sell_price;
    }

    public void setSingle_sell_price(double single_sell_price) {
        this.single_sell_price = single_sell_price;
    }

    public int getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(int storageCount) {
        this.storageCount = storageCount;
    }
}
