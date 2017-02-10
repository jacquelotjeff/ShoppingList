package com.clevermind.shoppinglist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.clevermind.shoppinglist.fragments.ProductCreateFragment;
import com.clevermind.shoppinglist.fragments.ProductEditFragment;
import com.clevermind.shoppinglist.fragments.ProductListFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListCreateFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListEditFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListShowFragment;
import com.clevermind.shoppinglist.managers.UserManager;
import com.clevermind.shoppinglist.models.Product;
import com.clevermind.shoppinglist.models.ShoppingList;

public class ShoppingListActivity extends BaseActivity implements
        ShoppingListFragment.OnFragmentInteractionListener,
        ShoppingListCreateFragment.OnFragmentInteractionListener,
        ShoppingListShowFragment.OnFragmentInteractionListener,
        ProductListFragment.OnFragmentInteractionListener,
        ShoppingListEditFragment.OnFragmentInteractionListener,
        ProductCreateFragment.OnFragmentInteractionListener,
        ProductEditFragment.OnFragmentInteractionListener {

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping_list);

        if (savedInstanceState == null && checkAccessNetwork()) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment shoppingListFragment = new ShoppingListFragment();
            ft.replace(R.id.fragmentContainer, shoppingListFragment, ShoppingListFragment.TAG);
            ft.commit();
        }
    }


    @Override
    public void onClickCreateListButton() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment createListFragment = new ShoppingListCreateFragment();
        ft.replace(R.id.fragmentContainer, createListFragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onClickLogoutButton() {

        new UserManager().removeTokenUser(this);
        Toast.makeText(this, R.string.message_logout_success, Toast.LENGTH_SHORT);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onClickShowButton(ShoppingList shoppingList) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment shoppingListShowFragment = ShoppingListShowFragment.newInstance(shoppingList);

        ft.replace(R.id.fragmentContainer, shoppingListShowFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClickListButton() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment listFragment = new ShoppingListFragment();
        ft.replace(R.id.fragmentContainer, listFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClickEditListButton(ShoppingList shoppingList) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment shoppingListEditFragment = ShoppingListEditFragment.newInstance(shoppingList);
        ft.replace(R.id.fragmentContainer, shoppingListEditFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClickAddProductButton(ShoppingList shoppingList) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment productCreateFragment = ProductCreateFragment.newInstance(shoppingList);
        ft.replace(R.id.fragmentContainer, productCreateFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClickEditProductButton(Product product) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment productEditFragment = ProductEditFragment.newInstance(product);
        ft.replace(R.id.fragmentContainer, productEditFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}