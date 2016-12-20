package com.clevermind.shoppinglist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.clevermind.shoppinglist.fragments.ProductListFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListCreateFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListEditFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListFragment;
import com.clevermind.shoppinglist.fragments.ShoppingListShowFragment;
import com.clevermind.shoppinglist.managers.UserManager;
import com.clevermind.shoppinglist.models.ShoppingList;
import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.ApiResponse;
import com.clevermind.shoppinglist.network.ApiTask;
import com.clevermind.shoppinglist.network.Request;
import com.clevermind.shoppinglist.utils.ErrorFormatter;

import java.util.HashMap;

public class ShoppingListActivity extends AppCompatActivity implements ShoppingListFragment.OnFragmentInteractionListener, ShoppingListCreateFragment.OnFragmentInteractionListener, ShoppingListShowFragment.OnFragmentInteractionListener, ProductListFragment.OnFragmentInteractionListener, ShoppingListEditFragment.OnFragmentInteractionListener, ApiTask.IApiTask {
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
        ApiTask apiRequest = new ApiTask();
        apiRequest.setListener(ShoppingListActivity.this);
        apiRequest.execute(buildRequestForDelete(shoppingList));
    }

    private Request buildRequestForDelete(ShoppingList shoppingList) {
        Request req = new Request();
        req.setMethod(Request.METHOD_GET);
        req.setUrl(ApiConst.URI_SHOPPING_LIST_REMOVE);

        HashMap<String, String > params = new HashMap<String, String>();
        String token = new UserManager().getTokenUser(this);

        params.put("token", token);
        params.put("id", shoppingList.getId().toString());

        req.setParams(params);

        return req;
    }

    @Override
    public void onApiFinished(ApiTask task, ApiResponse response) {
        String message = "";
        switch (response.getResultCode()) {
            case ApiConst.CODE_OK:
                message = getResources().getString(R.string.message_list_successfully_remove);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                onClickListButton();

                break;
            default:
                message = ErrorFormatter.formatError(this, response.getResultCode());
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                
                break;
        }
    }
}