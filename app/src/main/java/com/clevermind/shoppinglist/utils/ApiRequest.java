package com.clevermind.shoppinglist.utils;

import java.util.Map;

/**
 * Created by adrien on 23/11/16.
 */
public class ApiRequest {

    private static final String BASE_URI = "http://appspaces.fr/esgi/shopping_list/";

    private static final String URI_SUBSCRIBE = "account/subscribe.php";
    private static final String URI_LOGIN = "account/login.php";

    public static String request(String uri, Map params){
        return HttpRequest.append(BASE_URI + uri, params);
    }

    public static String subscribe(Map params){
        return request(URI_SUBSCRIBE, params);
    }

    public static String login(Map params){
        return request(URI_LOGIN, params);
    }
}
