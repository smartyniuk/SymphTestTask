package com.kuzya.footballexplorer;

import android.app.Application;

import com.kuzya.footballexplorer.services.DaggerStorageComponent;
import com.kuzya.footballexplorer.services.StorageComponent;
import com.kuzya.footballexplorer.services.StorageModule;


/**
 * Created by kuzya on 21.04.2016.
 */
public class FootballExplorerApplication extends Application {

    private StorageComponent mStorageComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mStorageComponent = DaggerStorageComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .storageModule(new StorageModule()).build();

    }

 /*   @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    public StorageComponent getStorageComponent() {
        return this.mStorageComponent;
    }
}
