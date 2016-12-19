package com.clevermind.shoppinglist.validators;

import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.clevermind.shoppinglist.R;
import com.clevermind.shoppinglist.models.ShoppingList;

/**
 * Created by adrien on 19/12/16.
 */

public class ShoppingListValidator {

    public static Boolean validate(ShoppingList shoppingList, View view){

        if (shoppingList.getName().trim().length() == 0) {
            TextInputLayout til = (TextInputLayout) view.findViewById(R.id.txtBoxNameGroup);
            til.setErrorEnabled(true);
            til.setError(view.getResources().getString(R.string.form_shopping_list_name_empty));

            return false;
        }

        return true;

    }
}
