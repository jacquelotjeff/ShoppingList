package com.clevermind.shoppinglist.models;

import java.util.Date;

public class ShoppingList implements java.io.Serializable {

    private Integer id;

    private String name;

    private Date createdDate;

    private Boolean completed;

    public ShoppingList(Integer id, String name, Boolean completed){

        this.id = id;
        this.name = name;
        this.completed = completed;

    }

    public ShoppingList(String name, Boolean completed){

        this.name = name;
        this.completed = completed;

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

    public Integer getCompletedInt(){
        return this.completed ? 1 : 0;
    }

}