package com.clevermind.shoppinglist.models;

import java.util.Date;

/**
 * Created by adrien on 28/11/16.
 */

public class ShoppingList {

    private Integer id;

    private String name;

    private Date createdDate;

    private Boolean completed;

    public ShoppingList(String name){

        this.name = name;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}