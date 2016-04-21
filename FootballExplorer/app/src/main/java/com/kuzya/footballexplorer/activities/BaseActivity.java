package com.kuzya.footballexplorer.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kuzya.footballexplorer.FootballExplorerApplication;
import com.kuzya.footballexplorer.R;
import com.kuzya.footballexplorer.services.StorageComponent;
import com.kuzya.footballexplorer.services.local.ILocalStorage;
import com.kuzya.footballexplorer.services.remote.BackendAPI;

import javax.inject.Inject;

/**
 * Created by kuzya on 21.04.2016.
 */
public class BaseActivity extends AppCompatActivity {
    @Inject
    protected BackendAPI _mBackendAPI;
    @Inject
    protected ILocalStorage mLocalStorageService;

    private boolean mIsRestarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getBackendComponent().inject(this);
    }

    StorageComponent getBackendComponent() {
        return ((FootballExplorerApplication) getApplication()).getStorageComponent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mIsRestarted) {
            slideInRightPageTransition();
        } else {
            slideInLeftPageTransition();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mIsRestarted = true;
    }

    protected void slideInRightPageTransition() {

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void slideInLeftPageTransition() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}
