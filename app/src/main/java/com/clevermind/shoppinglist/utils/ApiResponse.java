package com.clevermind.shoppinglist.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adrien on 23/11/16.
 */

public final class ApiResponse {

    public static final String HTTP_REQUEST_BASE = "http://appspaces.fr/esgi/shopping_list/";

    public static final String CODE_OK = "0";
    public static final String CODE_MISSING_RESQUIRED_PARAMS = "1";
    public static final String CODE_EMAIL_ALREADY_REGISTERED = "2";
    public static final String CODE_LOGIN_FAILED = "3";
    public static final String CODE_INVALID_TOKEN = "4";
    public static final String CODE_INTERNAL_SERVER_ERROR = "5";
    public static final String CODE_UNAUTHORIZED_ACTION = "6";
    public static final String CODE_NOTHING_TO_UPDATE = "7";

    private String resultCode;

    public ApiResponse(String json) {

        try {

            JSONObject jsonResult = new JSONObject(json);

            this.resultCode = (String) jsonResult.get("code");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getResultCode(){
        return this.resultCode;
    }
}
