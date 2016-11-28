package com.clevermind.shoppinglist.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.asynctask.RestTask;
import com.clevermind.shoppinglist.utils.ApiRequest;
import com.clevermind.shoppinglist.utils.ApiResponse;
import com.clevermind.shoppinglist.utils.Connectivity;
import com.clevermind.shoppinglist.utils.ErrorFormatter;

import java.util.HashMap;
import java.util.Map;

public class SubscribeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public SubscribeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SubscribeFragment.
     */
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View subscribeView = inflater.inflate(R.layout.fragment_subscribe, container, false);

        //On submit
        Button btnLinkSubscribe =  (Button) subscribeView.findViewById(R.id.btnSubmit);
        btnLinkSubscribe.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (Connectivity.isConnected(view.getContext())) {

                    Map<String, String> params = new HashMap<String, String>();

                    EditText txtBoxLastname = (EditText) getView().findViewById(R.id.txtBoxLastname);
                    EditText txtBoxFirstname = (EditText) getView().findViewById(R.id.txtBoxFirstname);
                    EditText txtBoxMail = (EditText) getView().findViewById(R.id.txtBoxMail);
                    EditText txtBoxPassword = (EditText) getView().findViewById(R.id.txtBoxPassword);

                    params.put("lastname", txtBoxLastname.getText().toString());
                    params.put("firstname", txtBoxFirstname.getText().toString());
                    params.put("password", txtBoxMail.getText().toString());
                    params.put("email", txtBoxPassword.getText().toString());

                    RestTask restTask = new RestTask(new RestTask.RestTaskListener() {
                        @Override
                        public void onRestTaskCompleted(String json) {

                            String message = "";

                            ApiResponse response = new ApiResponse(json);

                            switch (response.getResultCode()) {

                                case ApiResponse.CODE_OK:
                                    message = getResources().getString(R.string.message_subcribed_success);
                                    break;
                                case ApiResponse.CODE_EMAIL_ALREADY_REGISTERED:
                                    message = getResources().getString(R.string.message_subcribed_mail_already_registered);
                                    break;
                                default:
                                    message = ErrorFormatter.formatError(getActivity(), response.getResultCode());
                                    break;

                            }

                        //    FragmentTransaction ft = getFragmentManager().beginTransaction();
                          //  Fragment mainFragment = new MainFragment();
                           // ft.replace(R.id.layoutContainer, mainFragment);
                           //  ft.commit();

                            Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                    restTask.execute(ApiRequest.subscribe(params));

                } else {

                    Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.message_app_no_internet), Toast.LENGTH_LONG);
                    toast.show();

                }


            }
        });

        return subscribeView;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
