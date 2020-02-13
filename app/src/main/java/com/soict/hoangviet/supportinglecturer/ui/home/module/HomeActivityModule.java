package com.soict.hoangviet.supportinglecturer.ui.home.module;

import com.soict.hoangviet.supportinglecturer.ui.home.HomePresenter;
import com.soict.hoangviet.supportinglecturer.ui.home.HomePresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.home.HomeView;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeActivityModule {

    @Provides
    HomePresenter<HomeView> provideTeacherPresent(HomePresenterImpl<HomeView> mPresenter) {
        return mPresenter;
    }
}
