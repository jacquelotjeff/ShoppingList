package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.managers.ShoppingListManager;

public class ShoppingListShowFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final String SHOPPING_LIST_ID = "id";
    private String idShoppingList;

    public ShoppingListShowFragment() {
        // Required empty public constructor
    }

    public static ShoppingListShowFragment newInstance(Integer id) {
        ShoppingListShowFragment fragment = new ShoppingListShowFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idShoppingList = getArguments().getString(SHOPPING_LIST_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewLayout = inflater.inflate(R.layout.fragment_shopping_list_show, container, false);

        //Get the shopping list from manager.
        TextView lblName = (TextView) viewLayout.findViewById(R.id.lblName);
        //lblName.setText();

        return viewLayout;

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
        void onFragmentInteraction(Uri uri);
    }

}
