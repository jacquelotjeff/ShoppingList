package com.clevermind.shoppinglist.managers;

import com.clevermind.shoppinglist.network.ApiConst;

import org.json.JSONException;
import org.json.JSONObject;

public final class ApiResponse {

    public static final String HTTP_REQUEST_BASE = "http://appspaces.fr/esgi/shopping_list/";

    private String resultCode;
    private JSONObject result;

    public ApiResponse(String json) {
        try {
            JSONObject jsonResult = new JSONObject(json);

            this.resultCode = (String) jsonResult.get("code");

            if (resultCode.equals(ApiConst.CODE_OK)) {
                this.result = jsonResult.getJSONObject("result");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getResultCode() {
        return this.resultCode;
    }

    public JSONObject getResult() {
        return this.result;
    }
}
