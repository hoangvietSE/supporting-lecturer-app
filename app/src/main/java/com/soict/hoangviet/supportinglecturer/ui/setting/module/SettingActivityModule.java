package com.soict.hoangviet.supportinglecturer.ui.setting.module;

import com.soict.hoangviet.supportinglecturer.ui.setting.SettingPresenter;
import com.soict.hoangviet.supportinglecturer.ui.setting.SettingPresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.setting.SettingView;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingActivityModule {

    @Provides
    SettingPresenter<SettingView> provideMainPresenter(SettingPresenterImpl<SettingView> mMainPresenter) {
        return mMainPresenter;
    }
}
