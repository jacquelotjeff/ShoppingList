package com.clevermind.shoppinglist;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Goto subscribe activity
        Button btnLinkSubscribe = (Button) findViewById(R.id.btnLinkSubscribe);
        btnLinkSubscribe.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent subscribeIntent = new Intent(view.getContext(), SubscribeActivity.class);
                startActivity(subscribeIntent);

            }
        });

    }
}
