package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

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

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment implements ApiTask.IApiTask {

    private OnFragmentInteractionListener mListener;
    private ApiTask apiRequest;
    private ListAdapter mAdapter;

    private static final String STATE_LIST = "list";
    public static final String TAG = "shopping_list_fragment";

    private ArrayList<ShoppingList> mList;

    public interface OnFragmentInteractionListener {
        void onClickCreateListButton();
        void onClickShowButton(ShoppingList shoppingList);
    }

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

        View listLayout = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        // On configuration changes save the state and no API calls
        if (savedInstanceState == null) {

            apiRequest = new ApiTask();
            apiRequest.setListener(ShoppingListFragment.this);
            apiRequest.execute(buildRequestForList());

        } else {

            mList = (ArrayList<ShoppingList>) savedInstanceState.getSerializable(STATE_LIST);
            this.showData();

        }

        Button btnLinkListCreate = (Button) listLayout.findViewById(R.id.btnLinkShoppingListCreate);

        btnLinkListCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCreateListButton();
            }

        });

        return listLayout;

    }

    private void showData() {

        mAdapter = new ShoppingListAdapter(this.getActivity(), mList);

        ListView listView = (ListView) this.getView().findViewById(R.id.listViewShoppingList);
        listView.setAdapter(mAdapter);
        listView.deferNotifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingList shoppingList = (ShoppingList) mAdapter.getItem(i);
                onClickShowButton(shoppingList);
            }
        });
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

    public void onApiFinished(ApiTask task, ApiResponse result) {

        JSONArray jsonLists = result.getResultArray();
        ShoppingListManager shoppingListManager = new ShoppingListManager();
        mList = shoppingListManager.createFromResultArray(jsonLists);
        this.showData();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_LIST, mList);
    }

    public void onClickCreateListButton() {
        mListener.onClickCreateListButton();
    }

    public void onClickShowButton(ShoppingList shoppingList) {
        mListener.onClickShowButton(shoppingList);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (apiRequest != null) {
            apiRequest.cancel(true);
        }
    }

}
