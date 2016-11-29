package com.clevermind.shoppinglist.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.asynctask.RestTask;
import com.clevermind.shoppinglist.utils.ApiResponse;
import com.clevermind.shoppinglist.utils.Connectivity;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
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

        //On submit
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

                if (Connectivity.isConnected(view.getContext())) {

                    Map<String, String> params = new HashMap<String, String>();

                    EditText txtBoxMail = (EditText) getView().findViewById(R.id.txtBoxMail);
                    EditText txtBoxPassword = (EditText) getView().findViewById(R.id.txtBoxPassword);


                    RestTask restTask = new RestTask(new RestTask.RestTaskListener() {
                        @Override
                        public void onRestTaskCompleted(String json) {

                            String message = "";
                            ApiResponse response = new ApiResponse(json);

                            // TODO LOGIN THE USER
                        //    FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //    Fragment mainFragment = new MainFragment();
                        //    ft.replace(R.id.layoutContainer, mainFragment);
                        //    ft.commit();

                            Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                    //  restTask.execute(ApiRequest.login(params));

                } else {

                    Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.message_app_no_internet), Toast.LENGTH_LONG);
                    toast.show();

                }


            }
        });

        return loginView;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
        void onClickRegistrationButton();
    }
}
