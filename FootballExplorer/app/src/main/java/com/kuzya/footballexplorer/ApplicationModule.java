package com.kuzya.footballexplorer;

import android.content.Context;

import com.kuzya.footballexplorer.services.local.AppPrefs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kuzya on 21.04.2016.
 */
@Module
public class ApplicationModule {

    private final FootballExplorerApplication mApp;
    private final Context mContext;

    ApplicationModule(FootballExplorerApplication app) {
        mApp = app;
        mContext = app.getApplicationContext();
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    protected FootballExplorerApplication provideApplication() {
        return mApp;
    }

    @Provides
    @Singleton
    protected AppPrefs provideAppPrefs() {
        return new AppPrefs(mApp);
    }
}
