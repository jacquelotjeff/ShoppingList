package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.managers.ShoppingListManager;
import com.clevermind.shoppinglist.managers.UserManager;
import com.clevermind.shoppinglist.models.ShoppingList;
import com.clevermind.shoppinglist.models.User;
import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.ApiResponse;
import com.clevermind.shoppinglist.network.ApiTask;
import com.clevermind.shoppinglist.network.Request;
import com.clevermind.shoppinglist.utils.ErrorFormatter;

import java.util.HashMap;

public class ShoppingListCreateFragment extends Fragment implements ApiTask.IApiTask {

    private OnFragmentInteractionListener mListener;

    public ShoppingListCreateFragment() {

    }

    public static ShoppingListCreateFragment newInstance(String param1, String param2) {
        ShoppingListCreateFragment fragment = new ShoppingListCreateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View createView =  inflater.inflate(R.layout.fragment_shopping_list_create, container, false);

        Button btnSubmit = (Button) createView.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText txtName = (EditText) getView().findViewById(R.id.txtBoxName);

                boolean isValid = validForm(txtName);

                if (isValid) {
                    String name = txtName.getText().toString();
                    ShoppingList shoppingList = new ShoppingList(name);

                    ApiTask apiRequest = new ApiTask();
                    apiRequest.setListener(ShoppingListCreateFragment.this);
                    apiRequest.execute(buildRequestForCreate(shoppingList));
                }
            }

        });

        return createView;
    }

    private boolean validForm(EditText txtName) {
        String name = txtName.getText().toString();

        if (name.trim().length() == 0) {
            TextInputLayout til = (TextInputLayout) getView().findViewById(R.id.txtBoxNameGroup);
            til.setErrorEnabled(true);
            til.setError(getResources().getString(R.string.form_shopping_list_name_empty));

            return false;
        }

        return true;
    }


    private Request buildRequestForCreate(ShoppingList shoppingList){

        Request req = new Request();
        req.setMethod(Request.METHOD_GET);
        req.setUrl(ApiConst.URI_SHOPPING_LIST_CREATE);

        HashMap<String, String > params = new HashMap<String, String>();
        String token = new UserManager().getTokenUser(this.getActivity());

        params.put("token", token);
        params.put("name", shoppingList.getName());

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

                message = getResources().getString(R.string.message_list_successfully_created);
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                //Go to list
                onClickListButton();

                break;
            default:
                message = ErrorFormatter.formatError(getActivity(), response.getResultCode());
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void onClickListButton() {
        mListener.onClickListButton();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        void onClickListButton();
    }
}

