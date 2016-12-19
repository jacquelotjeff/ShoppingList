package com.clevermind.shoppinglist.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.managers.ShoppingListManager;
import com.clevermind.shoppinglist.models.ShoppingList;

public class ShoppingListShowFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final String SHOPPING_LIST_CHOICED = "shopping_list";
    private ShoppingList shoppingList;

    public interface OnFragmentInteractionListener {
        void onClickEditListButton(ShoppingList shoppingList);
        void onClickDeleteListButton(ShoppingList shoppingList);
    }

    public ShoppingListShowFragment() {

    }

    public static ShoppingListShowFragment newInstance(ShoppingList shoppingList) {
        ShoppingListShowFragment fragment = new ShoppingListShowFragment();

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

        ViewGroup viewLayout = (ViewGroup) inflater.inflate(R.layout.fragment_shopping_list_show, container, false);

        shoppingList = (ShoppingList) getArguments().getSerializable(SHOPPING_LIST_CHOICED);
        TextView lblName = (TextView) viewLayout.findViewById(R.id.lblName);
        lblName.setText(shoppingList.getName());

        Button btnEdit = (Button) viewLayout.findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEditListButton(shoppingList);
            }
        });

        Button btnDelete = (Button) viewLayout.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDeleteListButton(shoppingList);
            }
        });

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment productListFragment = ProductListFragment.newInstance(shoppingList);
        ft.add(R.id.productListFragmentContainer, productListFragment);
        ft.commit();


        return viewLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onClickEditListButton(ShoppingList shoppingList) {
        mListener.onClickEditListButton(shoppingList);
    }

    public void onClickDeleteListButton(ShoppingList shoppingList) {
        mListener.onClickDeleteListButton(shoppingList);
    }

}