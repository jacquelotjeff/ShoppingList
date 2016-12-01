package com.clevermind.shoppinglist.models;

import java.util.Date;

/**
 * Created by adrien on 28/11/16.
 */

public class Product {

    private Integer id;

    private String name;

    private Integer quantity;

    private float price;

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
