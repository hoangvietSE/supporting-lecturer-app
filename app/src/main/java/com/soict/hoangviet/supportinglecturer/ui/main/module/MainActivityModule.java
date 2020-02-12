package com.soict.hoangviet.supportinglecturer.ui.main.module;

import com.soict.hoangviet.supportinglecturer.ui.main.MainPresenter;
import com.soict.hoangviet.supportinglecturer.ui.main.MainPresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MainPresenter<MainView> provideMainPresenter(MainPresenterImpl<MainView> mMainPresenter) {
        return mMainPresenter;
    }
}
