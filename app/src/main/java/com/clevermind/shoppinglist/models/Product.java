package com.clevermind.shoppinglist.models;

import java.util.Date;

public class Product {

    private Integer id;

    private String name;

    private Integer quantity;

    private float price;

    public Product(Integer id, String name, Integer quantity, float price){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String name, Integer quantity, float price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
