package com.soict.hoangviet.supportinglecturer.di.module;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.BuildConfig;
import com.soict.hoangviet.supportinglecturer.data.network.ApiInterface;
import com.soict.hoangviet.supportinglecturer.data.network.NetworkConnectionInterceptor;
import com.soict.hoangviet.supportinglecturer.data.network.Repository;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        NetworkConnectionInterceptor networkConnectionInterceptor = new NetworkConnectionInterceptor(context);
        return httpClient
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(networkConnectionInterceptor)
                .build();
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ApiInterface.class);
    }

    @Provides
    @Singleton
    Repository provideRepository(ApiInterface apiInterface) {
        return new Repository(apiInterface);
    }
}
