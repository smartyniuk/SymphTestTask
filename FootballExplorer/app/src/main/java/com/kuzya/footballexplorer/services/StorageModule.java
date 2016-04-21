package com.kuzya.footballexplorer.services;

import android.content.Context;

import com.kuzya.footballexplorer.Const;
import com.kuzya.footballexplorer.services.local.DBLocalStorage;
import com.kuzya.footballexplorer.services.local.ILocalStorage;
import com.kuzya.footballexplorer.services.remote.BackendAPI;
import com.kuzya.footballexplorer.services.remote.RewriteCacheInterceptor;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kuzya on 21.04.2016.
 */
@Module
public class StorageModule {


    @Inject
    @Provides
    @Singleton
    BackendAPI provideBackendService(Context context) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new RewriteCacheInterceptor())

                //For local testing
                //{"error":"You reached your request limit. Get your free API token to use the API extensively."}
                //.addInterceptor(new LocalResponseInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(BackendAPI.class);
    }

    @Provides
    @Singleton
    ILocalStorage provideLocalStorage() {
        return new DBLocalStorage();
    }
}
