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
            TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
            TextView lblCreatedAt = (TextView) convertView.findViewById(R.id.lblCreatedAt);

            lblName.setText(shoppingList.getName());
            //lblName.setText(new SimpleDateFormat("dd/MM/yyyy").format(shoppingList.getCreatedDate()));
            lblCreatedAt.setText("00/00/000");
        }

        return convertView;
    }
}
