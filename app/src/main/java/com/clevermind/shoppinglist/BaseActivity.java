package com.clevermind.shoppinglist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jeffjacquelot on 07/01/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public boolean checkAccessNetwork() {
        Boolean permissionsVerified = true;

        //Check network state permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            permissionsVerified = false;

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, R.string.permission_network_state);

            }
        }

        //Check internet permission
        //Check network state permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            permissionsVerified = false;

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET}, R.string.permission_internet);

            }
        }

        return permissionsVerified;
    }
}
