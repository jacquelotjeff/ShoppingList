package com.clevermind.shoppinglist.managers;

import com.clevermind.shoppinglist.models.ShoppingList;
import com.clevermind.shoppinglist.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by adrien on 30/11/16.
 */

public class ShoppingListManager {

    public ShoppingList createFromResult(JSONObject json){

        try {

            ShoppingList shoppingList = new ShoppingList(json.getInt("id"), json.getString("name"));

            return shoppingList;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }

    public ArrayList<ShoppingList> createFromResultArray(JSONArray shoppingListArray){

        ArrayList<ShoppingList> list = new ArrayList<ShoppingList>();

        for (int i=0; i<shoppingListArray.length(); i++) {

            try {
                JSONObject shoppingListObject = shoppingListArray.getJSONObject(i);
                list.add(createFromResult(shoppingListObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return list;

    }
}
