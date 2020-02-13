package com.soict.hoangviet.supportinglecturer.ui.splash.module;

import com.soict.hoangviet.supportinglecturer.ui.splash.SplashPresenter;
import com.soict.hoangviet.supportinglecturer.ui.splash.SplashPresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.splash.SplashView;
import dagger.Module;
import dagger.Provides;

@Module
public class SplashActivityModule {

    @Provides
    SplashPresenter<SplashView> provideTeacherPresent(SplashPresenterImpl<SplashView> mPresenter) {
        return mPresenter;
    }
}
