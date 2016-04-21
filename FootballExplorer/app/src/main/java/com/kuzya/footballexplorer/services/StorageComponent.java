package com.kuzya.footballexplorer.services;

import com.kuzya.footballexplorer.ApplicationModule;
import com.kuzya.footballexplorer.activities.BaseActivity;
import com.kuzya.footballexplorer.activities.MainActivity;
import com.kuzya.footballexplorer.activities.TeamsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kuzya on 21.04.2016.
 */
@Singleton
@Component(modules = {StorageModule.class, ApplicationModule.class})
public interface StorageComponent {
    void inject(BaseActivity application);

    void inject(MainActivity application);

    void inject(TeamsActivity application);
}
