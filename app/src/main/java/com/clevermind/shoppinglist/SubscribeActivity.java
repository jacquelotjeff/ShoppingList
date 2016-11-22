package com.clevermind.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubscribeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_activity);

        // Submit subscription.
        Button btnLinkSubscribe = (Button) findViewById(R.id.btnSubmit);
        btnLinkSubscribe.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent subscribeIntent = new Intent(view.getContext(), SubscribeActivity.class);
                startActivity(subscribeIntent);

            }
        });
    }
}
