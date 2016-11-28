package com.clevermind.shoppinglist.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.asynctask.RestTask;
import com.clevermind.shoppinglist.model.User;
import com.clevermind.shoppinglist.utils.ApiRequest;
import com.clevermind.shoppinglist.utils.ApiResponse;
import com.clevermind.shoppinglist.utils.Connectivity;
import com.clevermind.shoppinglist.utils.ErrorFormatter;
import com.clevermind.shoppinglist.utils.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubscribeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubscribeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscribeFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;

    public SubscribeFragment() {
        // Required empty public constructor
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

            EditText txtBoxLastname = (EditText) getView().findViewById(R.id.txtBoxLastname);
            EditText txtBoxFirstname = (EditText) getView().findViewById(R.id.txtBoxFirstname);
            EditText txtBoxMail = (EditText) getView().findViewById(R.id.txtBoxMail);
            EditText txtBoxPassword = (EditText) getView().findViewById(R.id.txtBoxPassword);

            final User user = new User();

            user.setFirstname(txtBoxFirstname.getText().toString());
            user.setLastname(txtBoxLastname.getText().toString());
            user.setEmail(txtBoxMail.getText().toString());
            user.setPassword(txtBoxPassword.getText().toString());

            RestTask restTask = new RestTask(new RestTask.RestTaskListener() {
                @Override
                public void onRestTaskCompleted(String json) {

                    String message = "";

                    ApiResponse response = new ApiResponse(json);

                    switch (response.getResultCode()) {

                        case ApiResponse.CODE_OK:

                            message = getResources().getString(R.string.message_subcribed_success);

                            /*
                            //TODO Call MainFragment (Auto)
                            Bundle bundle = new Bundle();
                            bundle.putString("email", user.getEmail());
                            bundle.putString("password", user.getPassword());
                            FragmentTransaction ft = getFragmentManager().beginTransaction();

                            //TODO Replace by MainFragment
                            Fragment subscribeFragment = new SubscribeFragment();
                            subscribeFragment.setArguments(bundle);
                            ft.replace(R.id.layoutContainer, subscribeFragment);
                            ft.commit();
                            */

                            break;
                        case ApiResponse.CODE_EMAIL_ALREADY_REGISTERED:
                            message = getResources().getString(R.string.message_subcribed_mail_already_registered);
                            break;
                        default:
                            message = ErrorFormatter.formatError(getActivity(), response.getResultCode());
                            break;

                    }

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment mainFragment = new MainFragment();
                    ft.replace(R.id.layoutContainer, mainFragment);
                    ft.commit();

                    Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
                    toast.show();
                }
            });

            restTask.execute(ApiRequest.subscribe(user));


            }
        });

        return subscribeView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
