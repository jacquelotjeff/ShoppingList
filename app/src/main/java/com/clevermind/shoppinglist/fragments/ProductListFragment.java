package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.adapters.ProductAdapter;
import com.clevermind.shoppinglist.managers.ProductManager;
import com.clevermind.shoppinglist.managers.UserManager;
import com.clevermind.shoppinglist.models.Product;
import com.clevermind.shoppinglist.models.ShoppingList;
import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.ApiResponse;
import com.clevermind.shoppinglist.network.ApiTask;
import com.clevermind.shoppinglist.network.Request;
import com.clevermind.shoppinglist.utils.ErrorFormatter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductListFragment extends Fragment implements ApiTask.IApiTask, View.OnClickListener {

    private static final String SHOPPING_LIST_CHOICED = "shopping_list";

    private ShoppingList shoppingList;
    private ApiTask apiRequest;
    private static final String STATE_LIST_PRODUCT = "list";

    private static final String TASK_LIST = "task_list";
    private static final String TASK_DELETE = "task_delete";

    private OnFragmentInteractionListener mListener;
    private View mListView;

    private ArrayList<Product> mList;
    private ListAdapter mAdapter;

    public ProductListFragment() {
    }

    public static ProductListFragment newInstance(ShoppingList shoppingList) {
        ProductListFragment fragment = new ProductListFragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable(SHOPPING_LIST_CHOICED, shoppingList);

        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.mListView = inflater.inflate(R.layout.fragment_product_list, container, false);

        shoppingList = (ShoppingList) getArguments().getSerializable(SHOPPING_LIST_CHOICED);

        // On configuration changes save the state and no API calls
        if (savedInstanceState == null) {

            apiRequest = new ApiTask();
            apiRequest.setId(TASK_LIST);
            apiRequest.setListener(ProductListFragment.this);
            apiRequest.execute(buildRequestForList());

        } else {
            mList = (ArrayList<Product>) savedInstanceState.getSerializable(STATE_LIST_PRODUCT);
            this.showData();
        }

        return this.mListView;
    }

    public Request buildRequestForList() {

        Request request = new Request();

        request.setMethod(Request.METHOD_GET);
        request.setUrl(ApiConst.URI_PRODUCT_LIST);

        request.addParam("token", new UserManager().getTokenUser(this.getActivity()));
        request.addParam("shopping_list_id", shoppingList.getId().toString());

        return request;
    }

    private Request buildRequestForDelete(Product product) {

        Request req = new Request();
        req.setMethod(Request.METHOD_GET);
        req.setUrl(ApiConst.URI_PRODUCT_REMOVE);

        HashMap<String, String> params = new HashMap<String, String>();
        String token = new UserManager().getTokenUser(this.getActivity());

        params.put("token", token);
        params.put("id", product.getId().toString());

        req.setParams(params);

        return req;
    }

    public void onApiFinished(ApiTask task, ApiResponse result) {
        // Prevent disconnected
        if (result == null) {
            String message = getResources().getString(R.string.network_not_available);
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        } else {
            switch (task.getId()) {
                case TASK_LIST:
                    JSONArray jsonLists = result.getResultArray();
                    ProductManager productManager = new ProductManager();
                    mList = productManager.createFromResultArray(jsonLists, shoppingList);
                    this.showData();

                    break;

                case TASK_DELETE:
                    String message = "";
                    switch (result.getResultCode()) {
                        case ApiConst.CODE_OK:

                            message = getResources().getString(R.string.message_product_successfully_remove);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            onClickShowButton(shoppingList);

                            break;
                        default:
                            message = ErrorFormatter.formatError(getActivity(), result.getResultCode());
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
            }
        }
    }

    private void showData() {

        mAdapter = new ProductAdapter(this.getActivity(), mList, this);

        ListView listView = (ListView) this.mListView.findViewById(R.id.listViewProduct);
        listView.setAdapter(mAdapter);
        listView.deferNotifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_edit:
                onClickEditProductButton((Product) v.getTag());
                break;
            case R.id.button_delete:

                ApiTask apiRequest = new ApiTask();
                apiRequest.setId(TASK_DELETE);
                apiRequest.setListener(ProductListFragment.this);
                apiRequest.execute(buildRequestForDelete((Product) v.getTag()));

                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_LIST_PRODUCT, mList);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onClickEditProductButton(Product product);

        void onClickShowButton(ShoppingList shoppingList);
    }

    public void onClickEditProductButton(Product product) {
        mListener.onClickEditProductButton(product);
    }

    public void onClickShowButton(ShoppingList shoppingList) {
        mListener.onClickShowButton(shoppingList);
    }
}