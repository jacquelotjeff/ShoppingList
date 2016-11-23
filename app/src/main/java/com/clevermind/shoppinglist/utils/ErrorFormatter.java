package com.clevermind.shoppinglist.utils;

import android.content.Context;

import com.clevermind.shoppinglist.R;

/**
 * Created by adrien on 23/11/16.
 */

public class ErrorFormatter {

    public static String formatError(Context context, String code){

        String message = context.getResources().getString(R.string.message_app_error_occured);
        return String.format(message, code);

    }
}
