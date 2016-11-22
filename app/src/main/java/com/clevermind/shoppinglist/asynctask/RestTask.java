package com.clevermind.shoppinglist.asynctask;

import android.os.AsyncTask;

import com.clevermind.shoppinglist.utils.HttpRequest;

/**
 * Created by adrien on 22/11/16.
 */

public class RestTask extends AsyncTask<String, Void, String>{

    RestTaskListener listener;

    public RestTask(RestTaskListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {

        try {

            return HttpRequest.get(urls[0]).body();

        } catch (HttpRequest.HttpRequestException exception) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        listener.onRestTaskCompleted(response);
    }

    public interface RestTaskListener {
        void onRestTaskCompleted(String response);
    }

}
