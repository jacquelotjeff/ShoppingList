package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import com.clevermind.shoppinglist.models.ShoppingList;
import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.ApiResponse;
import com.clevermind.shoppinglist.network.ApiTask;
import com.clevermind.shoppinglist.network.Request;
import com.clevermind.shoppinglist.utils.ErrorFormatter;
import com.clevermind.shoppinglist.validators.ShoppingListValidator;

import java.util.HashMap;

public class ShoppingListEditFragment extends Fragment implements ApiTask.IApiTask {

    private OnFragmentInteractionListener mListener;
    private static final String SHOPPING_LIST_CHOICED = "shopping_list";
    private ShoppingList shoppingList;

    public ShoppingListEditFragment() {

    }

    public static ShoppingListEditFragment newInstance(ShoppingList shoppingList) {

        ShoppingListEditFragment fragment = new ShoppingListEditFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(SHOPPING_LIST_CHOICED, shoppingList);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View editView =  inflater.inflate(R.layout.fragment_shopping_list_type, container, false);

        TextView fragmentTitle = (TextView) editView.findViewById(R.id.lblFragmentTitle);
        fragmentTitle.setText(getResources().getString(R.string.app_shopping_list_edit));

        shoppingList = (ShoppingList) getArguments().getSerializable(SHOPPING_LIST_CHOICED);

        EditText txtName = (EditText) editView.findViewById(R.id.txtBoxName);
        txtName.setText(shoppingList.getName());

        CheckBox chkBoxCompleted = (CheckBox) editView.findViewById(R.id.chkBoxCompleted);
        chkBoxCompleted.setChecked(shoppingList.getCompleted());

        Button btnSubmit = (Button) editView.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText txtName = (EditText) getView().findViewById(R.id.txtBoxName);
                String name = txtName.getText().toString();
                shoppingList.setName(name);

                CheckBox chkBoxCompleted = (CheckBox) getView().findViewById(R.id.chkBoxCompleted);
                Boolean completed = chkBoxCompleted.isChecked();
                shoppingList.setCompleted(completed);

                boolean isValid = ShoppingListValidator.validate(shoppingList, getView());

                if (isValid) {
                    ApiTask apiRequest = new ApiTask();
                    apiRequest.setListener(ShoppingListEditFragment.this);
                    apiRequest.execute(buildRequestForEdit(shoppingList));
                }
            }

        });

        return editView;
    }
    private Request buildRequestForEdit(ShoppingList shoppingList){

        Request req = new Request();
        req.setMethod(Request.METHOD_GET);
        req.setUrl(ApiConst.URI_SHOPPING_LIST_EDIT);

        HashMap<String, String > params = new HashMap<String, String>();
        String token = new UserManager().getTokenUser(this.getActivity());

        params.put("token", token);
        params.put("id", shoppingList.getId().toString());
        params.put("name", shoppingList.getName());
        params.put("completed", shoppingList.getCompletedInt().toString());

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

                message = getResources().getString(R.string.message_list_successfully_edited);
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
        void onClickListButton();
    }
}

