package com.clevermind.shoppinglist.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.models.ShoppingList;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShoppingListAdapter extends BaseAdapter {

    private ArrayList<ShoppingList> shoppingLists;
    private LayoutInflater mInflater;

    public ShoppingListAdapter(Context context, ArrayList<ShoppingList> shoppingLists){
        this.shoppingLists = shoppingLists;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shoppingLists.size();
    }

    @Override
    public ShoppingList getItem(int i) {
        return shoppingLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return shoppingLists.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item_shopping_list, null);

        }

        ShoppingList shoppingList = getItem(position);

        if (shoppingList != null) {

            Log.d("DEBUG", shoppingList.getId().toString());
            Log.d("DEBUGGG", shoppingList.getCreatedDate().toString());
            TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
            TextView lblCreatedAt = (TextView) convertView.findViewById(R.id.lblCreatedAt);
            lblName.setText(shoppingList.getName());

            try {
                Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dateFormated = formatter.format(shoppingList.getCreatedDate());
                lblCreatedAt.setText(dateFormated);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }
}
