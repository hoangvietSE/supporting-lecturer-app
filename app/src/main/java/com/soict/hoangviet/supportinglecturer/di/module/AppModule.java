package com.soict.hoangviet.supportinglecturer.di.module;

import android.app.Application;
import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.SharePreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public
class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) { return application; }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    ISharePreference provideSharePreference(Context context) {
        return new SharePreference(context);
    }


}
