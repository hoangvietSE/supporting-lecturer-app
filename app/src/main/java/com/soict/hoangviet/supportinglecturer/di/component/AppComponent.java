package com.soict.hoangviet.supportinglecturer.di.component;

import android.app.Application;

import com.soict.hoangviet.supportinglecturer.application.LecturerApplication;
import com.soict.hoangviet.supportinglecturer.di.builder.ActivityBuilder;
import com.soict.hoangviet.supportinglecturer.di.builder.FragmentBuilder;
import com.soict.hoangviet.supportinglecturer.di.module.AppModule;
import com.soict.hoangviet.supportinglecturer.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuilder.class,
        FragmentBuilder.class,
        AppModule.class,
        NetworkModule.class})
public interface AppComponent {
    void inject(LecturerApplication baseApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
