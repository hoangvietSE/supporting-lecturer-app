package com.soict.hoangviet.supportinglecturer.application;

import android.app.Activity;
import android.app.Application;

import com.deploygate.sdk.DeployGate;
import com.soict.hoangviet.supportinglecturer.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class LecturerApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
        initDeployedGate();
    }

    private void initDeployedGate() {
        DeployGate.install(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
