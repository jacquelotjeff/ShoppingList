package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

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

import org.json.JSONArray;

import java.util.ArrayList;

public class ProductListFragment extends Fragment implements ApiTask.IApiTask {

    private static final String SHOPPING_LIST_CHOICED = "shopping_list";

    private ShoppingList shoppingList;
    private ApiTask apiRequest;
    private static final String STATE_LIST = "list";

    private OnFragmentInteractionListener mListener;

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

    public Request buildRequestForList(){

        Request request = new Request();

        request.setMethod(Request.METHOD_GET);
        request.setUrl(ApiConst.URI_PRODUCT_LIST);

        request.addParam("token", new UserManager().getTokenUser(this.getActivity()));
        request.addParam("shopping_list_id", shoppingList.getId().toString());

        return request;
    }

    public void onApiFinished(ApiTask task, ApiResponse result) {

        JSONArray jsonLists = result.getResultArray();
        ProductManager productManager = new ProductManager();
        mList = productManager.createFromResultArray(jsonLists);
        this.showData();

    }

    private void showData() {

        mAdapter = new ProductAdapter(this.getActivity(), mList);

        ListView listView = (ListView) this.getView().findViewById(R.id.listViewProduct);
        listView.setAdapter(mAdapter);
        listView.deferNotifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = (Product) mAdapter.getItem(i);
                //onClickShowButton(product);
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View listLayout = inflater.inflate(R.layout.fragment_product_list, container, false);

        shoppingList = (ShoppingList) getArguments().getSerializable(SHOPPING_LIST_CHOICED);

        // On configuration changes save the state and no API calls
        if (savedInstanceState == null) {

            apiRequest = new ApiTask();
            apiRequest.setListener(ProductListFragment.this);
            apiRequest.execute(buildRequestForList());

        } else {
            mList = (ArrayList<Product>) savedInstanceState.getSerializable(STATE_LIST);
            this.showData();
        }

        Button btnLinkListCreate = (Button) listLayout.findViewById(R.id.btnLinkProductCreate);

        btnLinkListCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onClickCreateListButton();
            }

        });


        return listLayout;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}