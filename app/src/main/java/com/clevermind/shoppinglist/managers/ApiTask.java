package com.clevermind.shoppinglist.managers;

import android.os.AsyncTask;
import android.util.Log;

import com.clevermind.shoppinglist.network.ApiConst;
import com.clevermind.shoppinglist.network.Request;
import com.clevermind.shoppinglist.utils.HttpRequest;

public class ApiTask extends AsyncTask<Request, Void, ApiResponse> {

    public interface IApiTask {
        public void onApiFinished(ApiTask task, ApiResponse result);
    }

    private Exception exception;
    private IApiTask listener;

    public void setListener(IApiTask listener) {
        this.listener = listener;
    }

    @Override
    protected ApiResponse doInBackground(Request... requests) {
        try {
            Request request = requests[0];
            String uri = HttpRequest.append(request.getUrl(), request.getParams());
            String result = "";
            Log.d("tata", uri);
            if (request.getMethod() == Request.METHOD_GET) {
                result = HttpRequest.get(uri).body();
            } else {
                result = HttpRequest.post(uri).body();
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
}
