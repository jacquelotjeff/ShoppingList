package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
    private View mListView;

    public interface OnFragmentInteractionListener {
        void onClickCreateListButton();
        void onClickLogoutButton();
        void onClickShowButton(ShoppingList shoppingList);
    }

    public ShoppingListFragment() {
    }

    public static ShoppingListFragment newInstance() {
        return new ShoppingListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.mListView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        getActivity().setTitle(R.string.app_shopping_list);
        // On configuration changes save the state and no API calls
        if (savedInstanceState == null) {
            executeRequest();
        } else {

            mList = (ArrayList<ShoppingList>) savedInstanceState.getSerializable(STATE_LIST);

            if(mList == null) {

                executeRequest();

            } else {

                this.showData();

            }
        }

        return mListView;

    }

    private void executeRequest(){
        apiRequest = new ApiTask();
        apiRequest.setListener(ShoppingListFragment.this);
        apiRequest.execute(buildRequestForList());
    }

    private void showData() {

        mAdapter = new ShoppingListAdapter(this.getActivity(), mList);

        ListView listView = (ListView) this.mListView.findViewById(R.id.listViewShoppingList);
        listView.setAdapter(mAdapter);
        listView.setClickable(true);
        listView.deferNotifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingList shoppingList = (ShoppingList) mAdapter.getItem(i);
                onClickShowButton(shoppingList);
            }
        });
    }

    public Request buildRequestForList() {

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
        // Prevent disconnected
        if (result == null) {
            String message = getResources().getString(R.string.network_not_available);
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        } else {
            JSONArray jsonLists = result.getResultArray();
            ShoppingListManager shoppingListManager = new ShoppingListManager();
            mList = shoppingListManager.createFromResultArray(jsonLists);
            this.showData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_LIST, mList);
    }

    public void onClickCreateListButton() {
        mListener.onClickCreateListButton();
    }

    public void onClickLogoutButton() {
        mListener.onClickLogoutButton();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_shopping_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                onClickCreateListButton();
                break;
            case R.id.action_logout:
                onClickLogoutButton();
                break;

        }

        return super.onOptionsItemSelected(item);

    }
}
