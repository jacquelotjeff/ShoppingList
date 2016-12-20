package com.clevermind.shoppinglist.managers;

import com.clevermind.shoppinglist.models.Product;
import com.clevermind.shoppinglist.models.ShoppingList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductManager {

    public Product createFromResult(JSONObject json){

        try {
            Product product = new Product(json.getString("name"), json.getInt("quantity"), json.getLong("price"));

            return product;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }

    public ArrayList<Product> createFromResultArray(JSONArray productArray){

        ArrayList<Product> list = new ArrayList<Product>();

        for (int i=0; i<productArray.length(); i++) {

            try {
                JSONObject shoppingListObject = productArray.getJSONObject(i);
                list.add(createFromResult(shoppingListObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return list;

    }

}
