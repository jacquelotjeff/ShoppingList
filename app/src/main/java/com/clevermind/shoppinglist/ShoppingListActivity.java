package com.clevermind.shoppinglist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.clevermind.shoppinglist.fragments.ProductListFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListCreateFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListEditFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListShowFragment;
import com.clevermind.shoppinglist.models.ShoppingList;

public class ShoppingListActivity extends AppCompatActivity implements ShoppingListFragment.OnFragmentInteractionListener, ShoppingListCreateFragment.OnFragmentInteractionListener, ShoppingListShowFragment.OnFragmentInteractionListener, ProductListFragment.OnFragmentInteractionListener, ShoppingListEditFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping_list);

        if (savedInstanceState == null) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment shoppingListFragment = new ShoppingListFragment();
            ft.replace(R.id.fragmentContainer, shoppingListFragment, ShoppingListFragment.TAG);
            ft.commit();

        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
    public void onClickDeleteListButton(ShoppingList shoppingList) {

    }
}