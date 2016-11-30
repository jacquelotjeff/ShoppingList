package com.clevermind.shoppinglist.managers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.clevermind.shoppinglist.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserManager extends ApiTask {

    public void logUser(User user, Activity context){

        SharedPreferences sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("token", user.getToken());
        editor.commit();

    }

    public User createFromResult(JSONObject json){

        try {

            User user = new User();

            user.setLastname(json.getString("lastname"));
            user.setFirstname(json.getString("firstname"));
            user.setEmail(json.getString("email"));
            user.setToken(json.getString("token"));

            return user;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }

}
