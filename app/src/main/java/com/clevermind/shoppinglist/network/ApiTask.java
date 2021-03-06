package com.clevermind.shoppinglist.network;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.clevermind.shoppinglist.utils.HttpRequest;

public class ApiTask extends AsyncTask<Request, Void, ApiResponse> {

    public interface IApiTask {
        void onApiFinished(ApiTask task, ApiResponse result);
    }

    private Exception exception;
    private IApiTask listener;
    private String id;

    public void setListener(IApiTask listener) {
        this.listener = listener;
    }

    public IApiTask getListener() {
        return this.listener;
    }

    @Override
    protected ApiResponse doInBackground(Request... requests) {

        try {

            Request request = requests[0];
            String result = "";

            if (request.getMethod() == Request.METHOD_GET) {
                result = HttpRequest.get(request.getUrl(), request.getParams(), true).body();
            } else {
                result = HttpRequest.post(request.getUrl(), request.getParams(), true).body();
            }

            return new ApiResponse(result);
        } catch (HttpRequest.HttpRequestException exception) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(ApiResponse result) {
        this.listener.onApiFinished(this, result);
    }

    public String getId(){ return this.id; }

    public void setId(String id){ this.id = id; }
}
