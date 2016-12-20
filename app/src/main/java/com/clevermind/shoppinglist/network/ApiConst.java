package com.clevermind.shoppinglist.network;

public class ApiConst {

    // URI
    public static final String BASE_URI = "http://appspaces.fr/esgi/shopping_list/";
    public static final String URI_SUBSCRIBE = BASE_URI+"account/subscribe.php";
    public static final String URI_LOGIN = BASE_URI+"account/login.php";
    public static final String URI_SHOPPING_LIST_CREATE = BASE_URI+"shopping_list/create.php";
    public static final String URI_SHOPPING_LIST_EDIT = BASE_URI+"shopping_list/edit.php";
    public static final String URI_SHOPPING_LIST_REMOVE = BASE_URI+"shopping_list/remove.php";
    public static final String URI_SHOPPING_LIST = BASE_URI+"shopping_list/list.php";
    public static final String URI_PRODUCT_LIST = BASE_URI+"product/list.php";

    // Errors code
    public static final String CODE_OK = "0";
    public static final String CODE_MISSING_RESQUIRED_PARAMS = "1";
    public static final String CODE_EMAIL_ALREADY_REGISTERED = "2";
    public static final String CODE_LOGIN_FAILED = "3";
    public static final String CODE_INVALID_TOKEN = "4";
    public static final String CODE_INTERNAL_SERVER_ERROR = "5";
    public static final String CODE_UNAUTHORIZED_ACTION = "6";
    public static final String CODE_NOTHING_TO_UPDATE = "7";

}
