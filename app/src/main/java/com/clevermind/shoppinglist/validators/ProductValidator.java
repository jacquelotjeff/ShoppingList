package com.clevermind.shoppinglist.validators;

import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.models.Product;
import com.clevermind.shoppinglist.models.ShoppingList;

public class ProductValidator {

    public static Boolean validate(String name, String quantity, String price, View view) {
        boolean isValid = true;
        if (name.trim().length() == 0) {
            TextInputLayout til = (TextInputLayout) view.findViewById(R.id.txtBoxNameGroup);
            til.setErrorEnabled(true);
            til.setError(view.getResources().getString(R.string.form_product_name_empty));

            isValid = false;
        }

        if (quantity.trim().length() == 0) {
            TextInputLayout til = (TextInputLayout) view.findViewById(R.id.txtBoxQuantityGroup);
            til.setErrorEnabled(true);
            til.setError(view.getResources().getString(R.string.form_product_quantity_empty));

            isValid = false;
        }

        if (price.trim().length() == 0) {
            TextInputLayout til = (TextInputLayout) view.findViewById(R.id.txtBoxPriceGroup);
            til.setErrorEnabled(true);
            til.setError(view.getResources().getString(R.string.form_product_price_empty));

            isValid = false;
        }

        return isValid;
    }
}
