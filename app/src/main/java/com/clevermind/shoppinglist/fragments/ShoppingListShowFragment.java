package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.managers.UserManager;
import com.clevermind.shoppinglist.models.ShoppingList;
import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.ApiResponse;
import com.clevermind.shoppinglist.network.ApiTask;
import com.clevermind.shoppinglist.network.Request;
import com.clevermind.shoppinglist.utils.ErrorFormatter;

import java.util.HashMap;

public class ShoppingListShowFragment extends Fragment implements ApiTask.IApiTask {

    private OnFragmentInteractionListener mListener;
    private static final String SHOPPING_LIST_CHOICED = "shopping_list";
    private ShoppingList shoppingList;

    public interface OnFragmentInteractionListener {
        void onClickEditListButton(ShoppingList shoppingList);
        void onClickAddProductButton(ShoppingList shoppingList);
        void onClickListButton();
    }

    public ShoppingListShowFragment() {

    }

    public static ShoppingListShowFragment newInstance(ShoppingList shoppingList) {
        ShoppingListShowFragment fragment = new ShoppingListShowFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(SHOPPING_LIST_CHOICED, shoppingList);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup viewLayout = (ViewGroup) inflater.inflate(R.layout.fragment_shopping_list_show, container, false);

        shoppingList = (ShoppingList) getArguments().getSerializable(SHOPPING_LIST_CHOICED);
        getActivity().setTitle(shoppingList.getName());

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment productListFragment = ProductListFragment.newInstance(shoppingList);
        ft.add(R.id.productListFragmentContainer, productListFragment);
        ft.commit();


        return viewLayout;
    }

    private Request buildRequestForDelete(ShoppingList shoppingList) {

        Request req = new Request();
        req.setMethod(Request.METHOD_GET);
        req.setUrl(ApiConst.URI_SHOPPING_LIST_REMOVE);

        HashMap<String, String> params = new HashMap<String, String>();
        String token = new UserManager().getTokenUser(this.getActivity());

        params.put("token", token);
        params.put("id", shoppingList.getId().toString());

        req.setParams(params);

        return req;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onClickEditListButton(ShoppingList shoppingList) {
        mListener.onClickEditListButton(shoppingList);
    }

    public void onClickAddProductButton(ShoppingList shoppingList) {
        mListener.onClickAddProductButton(shoppingList);
    }

    public void deleteList(ShoppingList shoppingList) {
        ApiTask apiRequest = new ApiTask();
        apiRequest.setListener(ShoppingListShowFragment.this);
        apiRequest.execute(buildRequestForDelete(shoppingList));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_shopping_list_show, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Ce switch aurait fonctionnait si JEFF avait mis des BREAKS !!!
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteList(shoppingList);
                break;
            case R.id.action_edit:
                onClickEditListButton(shoppingList);
                break;
            case R.id.action_add:
                onClickAddProductButton(shoppingList);
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onApiFinished(ApiTask task, ApiResponse response) {
        String message = "";

        // Prevent disconnected
        if (response == null) {
            message = getResources().getString(R.string.network_not_available);
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        } else {
            switch (response.getResultCode()) {
                case ApiConst.CODE_OK:
                    message = getResources().getString(R.string.message_list_successfully_remove);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    mListener.onClickListButton();
                    break;
                default:
                    message = ErrorFormatter.formatError(getActivity(), response.getResultCode());
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}