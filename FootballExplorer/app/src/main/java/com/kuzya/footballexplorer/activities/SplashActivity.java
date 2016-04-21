package com.kuzya.footballexplorer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by kuzya on 21.04.2016.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, MainActivity.class));

        finish();
    }
}
