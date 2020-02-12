package com.soict.hoangviet.supportinglecturer.di.component;

import android.app.Application;

import com.soict.hoangviet.supportinglecturer.application.LecturerApplication;
import com.soict.hoangviet.supportinglecturer.di.builder.ActivityBuilder;
import com.soict.hoangviet.supportinglecturer.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuilder.class,
        AppModule.class})
public interface AppComponent {
    void inject(LecturerApplication baseApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
