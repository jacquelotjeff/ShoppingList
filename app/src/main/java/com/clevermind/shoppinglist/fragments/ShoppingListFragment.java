package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.adapters.ShoppingListAdapter;
import com.clevermind.shoppinglist.managers.ShoppingListManager;
import com.clevermind.shoppinglist.managers.UserManager;
import com.clevermind.shoppinglist.models.ShoppingList;
import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.ApiResponse;
import com.clevermind.shoppinglist.network.ApiTask;
import com.clevermind.shoppinglist.network.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingListFragment extends Fragment implements ApiTask.IApiTask {

    private OnFragmentInteractionListener mListener;

    public ShoppingListFragment() {
    }

    public static ShoppingListFragment newInstance(String param1, String param2) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View listView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        ApiTask apiRequest = new ApiTask();
        apiRequest.setListener(ShoppingListFragment.this);
        apiRequest.execute(buildRequestForList());


        Button btnLinkListCreate = (Button) listView.findViewById(R.id.btnLinkShoppingListCreate);

        btnLinkListCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCreateListButton();
            }

        });

        return listView;


    }

    public Request buildRequestForList(){

        Request request = new Request();
        request.setMethod(Request.METHOD_GET);
        request.setUrl(ApiConst.URI_SHOPPING_LIST);
        request.addParam("token", new UserManager().getTokenUser(this.getActivity()));

        return request;

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

    @Override
    public void onApiFinished(ApiTask task, ApiResponse result) {

        JSONArray jsonLists = result.getResultArray();
        ShoppingListManager shoppingListManager = new ShoppingListManager();
        ArrayList<ShoppingList> list = shoppingListManager.createFromResultArray(jsonLists);
        ShoppingListAdapter adapter = new ShoppingListAdapter(this.getActivity(), list);
        ListView listView = (ListView) this.getView().findViewById(R.id.listViewShoppingList);
        listView.setAdapter(adapter);

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        void onClickCreateListButton();
    }

    public void onClickCreateListButton() {
        mListener.onClickCreateListButton();
    }
}
