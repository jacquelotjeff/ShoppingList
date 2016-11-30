package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.managers.ApiResponse;
import com.clevermind.shoppinglist.managers.ApiTask;
import com.clevermind.shoppinglist.managers.UserManager;
import com.clevermind.shoppinglist.models.User;
import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.Request;
import com.clevermind.shoppinglist.utils.ErrorFormatter;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Set;

public class LoginFragment extends Fragment implements ApiTask.IApiTask {
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SubscribeFragment.
     */
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_login, container, false);

        Button btnLinkLogin = (Button) loginView.findViewById(R.id.btnSubmit);
        TextView txtRegistration = (TextView) loginView.findViewById(R.id.txtLinkSubscribe);

        txtRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickRegistrationButton();
            }

        });

        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText txtBoxMail = (EditText) getView().findViewById(R.id.txtBoxMail);
                EditText txtBoxPassword = (EditText) getView().findViewById(R.id.txtBoxPassword);

                String mail = txtBoxMail.getText().toString();
                String password = txtBoxPassword.getText().toString();

                final User user = new User(mail, password);

                ApiTask apiRequest = new ApiTask();
                apiRequest.setListener(LoginFragment.this);
                apiRequest.execute(buildRequestForLogin(user));

            }
        });

        return loginView;
    }

    private Request buildRequestForLogin(User user) {
        HashMap<String, String> params = new HashMap<>();

        params.put("password", user.getPassword());
        params.put("email", user.getEmail());

        Request req = new Request();
        req.setParams(params);
        req.setMethod(Request.METHOD_GET);
        req.setUrl(ApiConst.URI_LOGIN);

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

    public void OnClickRegistrationButton() {
        mListener.onClickRegistrationButton();
    }

    @Override
    public void onApiFinished(ApiTask task, ApiResponse response) {

        String message = "";
        switch (response.getResultCode()) {
            case ApiConst.CODE_OK:

                UserManager userManager = new UserManager();
                User user = userManager.createFromResult(response.getResult());
                userManager.logUser(user, this.getActivity());

                message = getResources().getString(R.string.message_login_success);
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                break;
            case ApiConst.CODE_LOGIN_FAILED:
                message = getResources().getString(R.string.message_login_failed);
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
        void onClickRegistrationButton();
    }
}