package com.clevermind.shoppinglist.network;

import java.util.HashMap;

public class Request {
    public static String METHOD_POST = "POST";
    public static String METHOD_GET = "GET";

    private String url;
    private String method;
    private HashMap<String, String> params;

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
