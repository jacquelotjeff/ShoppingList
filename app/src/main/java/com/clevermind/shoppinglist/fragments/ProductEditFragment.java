package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;

public class ProductEditFragment extends Fragment implements ApiTask.IApiTask {

    private OnFragmentInteractionListener mListener;
    private Product product;
    private static final String PRODUCT_CHOICED = "shopping_list";

    public ProductEditFragment() {
    }

    public static ProductEditFragment newInstance(Product product) {
        ProductEditFragment fragment = new ProductEditFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCT_CHOICED, product);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View editView =  inflater.inflate(R.layout.fragment_product_type, container, false);

        product = (Product) getArguments().getSerializable(PRODUCT_CHOICED);

        TextView fragmentTitle = (TextView) editView.findViewById(R.id.lblFragmentTitle);

        fragmentTitle.setText(getResources().getString(R.string.app_product_edit));

        EditText txtName = (EditText) editView.findViewById(R.id.txtBoxName);
        txtName.setText(product.getName());

        EditText txtBoxQuantity = (EditText) editView.findViewById(R.id.txtBoxQuantity);
        txtBoxQuantity.setText(String.valueOf(product.getQuantity()));

        EditText txtBoxPrice = (EditText) editView.findViewById(R.id.txtBoxPrice);
        txtBoxPrice.setText(String.valueOf(product.getPrice()));

        Button btnSubmit = (Button) editView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Get form values
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
                    apiRequest.setListener(ProductEditFragment.this);
                    apiRequest.execute(buildRequestForEdit((product)));
                }
            }

        });

        return editView;
    }

    private Request buildRequestForEdit(Product product){

        Request req = new Request();
        req.setMethod(Request.METHOD_GET);
        req.setUrl(ApiConst.URI_PRODUCT_EDIT);

        HashMap<String, String > params = new HashMap<String, String>();
        String token = new UserManager().getTokenUser(this.getActivity());

        params.put("token", token);
        params.put("id", this.product.getId().toString());
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
        switch (response.getResultCode()) {
            case ApiConst.CODE_OK:

                message = getResources().getString(R.string.message_product_successfully_edited);
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                onClickShowButton(product.getShoppingList());

                break;

            default:

                message = ErrorFormatter.formatError(getActivity(), response.getResultCode());
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                onClickShowButton(product.getShoppingList());

                break;
        }

    }

    public void onClickShowButton(ShoppingList shoppingList) {
        mListener.onClickShowButton(shoppingList);
    }

    public interface OnFragmentInteractionListener {
        void onClickShowButton(ShoppingList shoppingList);
    }
}

