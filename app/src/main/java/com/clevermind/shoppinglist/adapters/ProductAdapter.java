package com.clevermind.shoppinglist.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.models.Product;
import com.clevermind.shoppinglist.models.ShoppingList;

import java.util.ArrayList;

/**
 * Created by adrien on 14/12/16.
 */

public class ProductAdapter  extends BaseAdapter {

    private ArrayList<Product> products;
    private LayoutInflater mInflater;
    private View.OnClickListener mListener;

    public ProductAdapter(Context context, ArrayList<Product> products, View.OnClickListener mListener){
        this.mListener = mListener;
        this.products = products;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return products.get(i).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item_product, null);

        }

        Product product = getItem(position);

        if (product != null) {

            TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
            TextView lblCreatedAt = (TextView) convertView.findViewById(R.id.lblQuantity);
            TextView lblPrice = (TextView) convertView.findViewById(R.id.lblPrice);

            lblName.setText(product.getName());
            lblPrice.setText(product.getQuantity().toString());
            lblCreatedAt.setText(String.valueOf(product.getPrice()));

            ImageButton btnEdit = (ImageButton) convertView.findViewById(R.id.button_edit);
            btnEdit.setTag(getItem(position));
            btnEdit.setOnClickListener(mListener);

            ImageButton btnDelete = (ImageButton) convertView.findViewById(R.id.button_delete);
            btnDelete.setTag(getItem(position));
            btnDelete.setOnClickListener(mListener);

        }

        return convertView;
    }

}

