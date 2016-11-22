package com.clevermind.shoppinglist;


import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.clevermind.shoppinglist.fragment.MainFragment;
import com.clevermind.shoppinglist.fragment.SubscribeFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, SubscribeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean permissionsVerified = true;

        //Check network state permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            permissionsVerified = false;

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, R.string.permission_network_state);

            }

        }

        //Check internet permission
        //Check network state permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            permissionsVerified = false;

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET}, R.string.permission_internet);

            }

        }

        if (permissionsVerified) {

            //Main fragment
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment mainFragment = new MainFragment();
            fragmentTransaction.replace(R.id.layoutContainer, mainFragment);
            fragmentTransaction.commit();

        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
