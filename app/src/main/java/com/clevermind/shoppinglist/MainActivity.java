package com.clevermind.shoppinglist;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;

import com.clevermind.shoppinglist.fragments.LoginFragment;
import com.clevermind.shoppinglist.fragments.SubscribeFragment;
import com.clevermind.shoppinglist.managers.UserManager;

public class MainActivity extends BaseActivity implements SubscribeFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkAccessNetwork()) {
            // By default add the login fragment
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment loginFragment = new LoginFragment();
            ft.replace(R.id.layoutContainer, loginFragment);
            ft.commit();
        }

        String token = new UserManager().getTokenUser(this);

        if (token != "") {
            Log.d("INFO", token);
            startShoppingListActivity();
        }
    }

    public void startShoppingListActivity() {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        //Start the new activity as the root Task (Exit the app instead of go identification)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClickLoginButton() {
        // By default add the login fragment
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment loginFragment = new LoginFragment();
        ft.replace(R.id.layoutContainer, loginFragment);
        //On login fragment clear back stack.
        fm.popBackStack();
        ft.commit();
    }

    @Override
    public void onRegistrationFinish() {
        startShoppingListActivity();
    }

    @Override
    public void onClickRegistrationButton() {
        // By default add the login fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment subscribeFragment = new SubscribeFragment();
        ft.replace(R.id.layoutContainer, subscribeFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
