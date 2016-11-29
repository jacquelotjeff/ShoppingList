package com.clevermind.shoppinglist.managers;

import org.json.JSONException;
import org.json.JSONObject;

public final class ApiResponse {

    public static final String HTTP_REQUEST_BASE = "http://appspaces.fr/esgi/shopping_list/";

    private String resultCode;
    private String result;

    public ApiResponse(String json) {
        try {
            JSONObject jsonResult = new JSONObject(json);
            this.resultCode = (String) jsonResult.get("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getResultCode() {
        return this.resultCode;
    }

    public String getResult() {
        return this.result;
    }
}
