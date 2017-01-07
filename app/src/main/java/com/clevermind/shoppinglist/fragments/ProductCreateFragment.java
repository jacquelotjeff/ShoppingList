package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.managers.UserManager;
import com.clevermind.shoppinglist.models.Product;
import com.clevermind.shoppinglist.models.ShoppingList;
import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.ApiResponse;
import com.clevermind.shoppinglist.network.ApiTask;
import com.clevermind.shoppinglist.network.Request;
import com.clevermind.shoppinglist.utils.ErrorFormatter;
import com.clevermind.shoppinglist.validators.ProductValidator;
import com.clevermind.shoppinglist.validators.ShoppingListValidator;

import java.util.HashMap;

public class ProductCreateFragment extends Fragment implements ApiTask.IApiTask {

    private OnFragmentInteractionListener mListener;
    private ShoppingList shoppingList;
    private static final String SHOPPING_LIST_CHOICED = "shopping_list";

    public ProductCreateFragment() {
    }

    public static ProductCreateFragment newInstance(ShoppingList shoppingList) {
        ProductCreateFragment fragment = new ProductCreateFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(SHOPPING_LIST_CHOICED, shoppingList);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View createView = inflater.inflate(R.layout.fragment_product_type, container, false);

        shoppingList = (ShoppingList) getArguments().getSerializable(SHOPPING_LIST_CHOICED);

        TextView fragmentTitle = (TextView) createView.findViewById(R.id.lblFragmentTitle);
        fragmentTitle.setText(getResources().getString(R.string.app_product_create));

        Button btnSubmit = (Button) createView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText txtName = (EditText) getView().findViewById(R.id.txtBoxName);
                String name = txtName.getText().toString();

                EditText txtBoxQuantity = (EditText) getView().findViewById(R.id.txtBoxQuantity);
                String quantity = txtBoxQuantity.getText().toString();

                EditText txtBoxPrice = (EditText) getView().findViewById(R.id.txtBoxPrice);
                String price = txtBoxPrice.getText().toString();

                boolean isValid = ProductValidator.validate(name, quantity, price, getView());

                if (isValid) {
                    Product product = new Product(name, Integer.parseInt(quantity), Float.parseFloat(price));
                    ApiTask apiRequest = new ApiTask();
                    apiRequest.setListener(ProductCreateFragment.this);
                    apiRequest.execute(buildRequestForCreate(product));
                }
            }

        });

        return createView;
    }

    private Request buildRequestForCreate(Product product) {

        Request req = new Request();
        req.setMethod(Request.METHOD_GET);
        req.setUrl(ApiConst.URI_PRODUCT_CREATE);

        HashMap<String, String> params = new HashMap<String, String>();
        String token = new UserManager().getTokenUser(this.getActivity());

        params.put("token", token);
        params.put("shopping_list_id", this.shoppingList.getId().toString());
        params.put("name", product.getName());
        params.put("quantity", String.valueOf(product.getQuantity()));
        params.put("price", String.valueOf(product.getPrice()));

        req.setParams(params);

        return req;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                    message = getResources().getString(R.string.message_product_successfully_created);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    onClickListProductsButton();

                    break;
                default:
                    message = ErrorFormatter.formatError(getActivity(), response.getResultCode());
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public void onClickListProductsButton() {
        mListener.onClickShowButton(this.shoppingList);
    }

    public interface OnFragmentInteractionListener {
        void onClickShowButton(ShoppingList shoppingList);
    }
}

