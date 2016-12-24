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

            Product product = new Product(json.getInt("id"), json.getString("name"), json.getInt("quantity"), json.getLong("price"));

            return product;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }

    public ArrayList<Product> createFromResultArray(JSONArray productArray, ShoppingList shoppingList){

        ArrayList<Product> list = new ArrayList<Product>();

        for (int i=0; i<productArray.length(); i++) {

            try {

                JSONObject productObject = productArray.getJSONObject(i);

                Product product = createFromResult(productObject);
                if (shoppingList != null) {
                    product.setShoppingList(shoppingList);
                }

                list.add(product);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return list;

    }

}
