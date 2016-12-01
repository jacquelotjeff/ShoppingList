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
import com.clevermind.shoppinglist.network.ApiTask;
import com.clevermind.shoppinglist.managers.UserManager;
import com.clevermind.shoppinglist.models.User;
import com.clevermind.shoppinglist.network.ApiResponse;
import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.Request;
import com.clevermind.shoppinglist.utils.ErrorFormatter;

import java.util.HashMap;

public class SubscribeFragment extends Fragment implements ApiTask.IApiTask {
    private OnFragmentInteractionListener mListener;

    public SubscribeFragment() {
    }

    public static SubscribeFragment newInstance(String param1, String param2) {
        SubscribeFragment fragment = new SubscribeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View subscribeView = inflater.inflate(R.layout.fragment_subscribe, container, false);

        Button btnLinkSubscribe = (Button) subscribeView.findViewById(R.id.btnSubmit);
        TextView txtLogin = (TextView) subscribeView.findViewById(R.id.txtLinkLogin);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickLoginButton();
            }
        });

        btnLinkSubscribe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText txtBoxLastname = (EditText) getView().findViewById(R.id.txtBoxLastname);
                EditText txtBoxFirstname = (EditText) getView().findViewById(R.id.txtBoxFirstname);
                EditText txtBoxMail = (EditText) getView().findViewById(R.id.txtBoxMail);
                EditText txtBoxPassword = (EditText) getView().findViewById(R.id.txtBoxPassword);

                String userLastname = txtBoxLastname.getText().toString();
                String userFirstname = txtBoxFirstname.getText().toString();
                String userEmail = txtBoxMail.getText().toString();
                String userPassword = txtBoxPassword.getText().toString();

                final User user = new User(userLastname, userFirstname, userEmail, userPassword);

                ApiTask apiRequest = new ApiTask();
                apiRequest.setListener(SubscribeFragment.this);
                apiRequest.execute(buildRequestForSubscribe(user));
            }

        });

        return subscribeView;
    }

    private Request buildRequestForSubscribe(User user) {
        HashMap<String, String> params = new HashMap<>();
        params.put("lastname", user.getLastname());
        params.put("firstname", user.getFirstname());
        params.put("password", user.getPassword());
        params.put("email", user.getEmail());

        Request req = new Request();
        req.setParams(params);
        req.setMethod(Request.METHOD_GET);
        req.setUrl(ApiConst.URI_SUBSCRIBE);

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

    public void OnClickLoginButton() {
        mListener.onClickLoginButton();
    }

    @Override
    public void onApiFinished(ApiTask task, ApiResponse response) {
        String message = "";
        switch (response.getResultCode()) {
            case ApiConst.CODE_OK:

                UserManager userManager = new UserManager();
                User user = userManager.createFromResult(response.getResultObject());
                userManager.logUser(user, this.getActivity());

                message = getResources().getString(R.string.message_subcribed_success);
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                OnClickLoginButton();
                break;
            case ApiConst.CODE_EMAIL_ALREADY_REGISTERED:
                message = getResources().getString(R.string.message_subcribed_mail_already_registered);

                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                break;
            default:
                message = ErrorFormatter.formatError(getActivity(), response.getResultCode());
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
        void onClickLoginButton();
    }
}
