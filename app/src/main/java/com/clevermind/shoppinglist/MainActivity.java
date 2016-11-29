package com.clevermind.shoppinglist;


import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.clevermind.shoppinglist.fragment.LoginFragment;
import com.clevermind.shoppinglist.fragment.SubscribeFragment;

public class MainActivity extends AppCompatActivity implements SubscribeFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

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

            // By default add the login fragment
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment loginFragment = new LoginFragment();
            ft.replace(R.id.layoutContainer, loginFragment);
            ft.addToBackStack(null);
            ft.commit();

        }

    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void onClickLoginButton() {
        // By default add the login fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment loginFragment = new LoginFragment();
        ft.replace(R.id.layoutContainer, loginFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onClickRegistrationButton() {
        // By default add the login fragment
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment subscribeFragment = new SubscribeFragment();
        ft.replace(R.id.layoutContainer, subscribeFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
