package com.clevermind.shoppinglist.network;

import com.clevermind.shoppinglist.network.ApiConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ApiResponse {
    private String resultCode;
    private JSONObject result;

    public ApiResponse(String json) {
        try {

            this.result = new JSONObject(json);
            this.resultCode = (String) this.result.get("code");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getResultCode() {
        return this.resultCode;
    }

    public JSONArray getResultArray() {
        try {
            return this.result.getJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getResultObject() {
        try {
            return this.result.getJSONObject("result");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
