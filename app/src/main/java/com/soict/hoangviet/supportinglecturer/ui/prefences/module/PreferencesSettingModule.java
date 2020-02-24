package com.soict.hoangviet.supportinglecturer.ui.prefences.module;

import com.soict.hoangviet.supportinglecturer.ui.prefences.PreferencesSettingPresenter;
import com.soict.hoangviet.supportinglecturer.ui.prefences.PreferencesSettingPresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.prefences.PreferencesSettingView;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesSettingModule {

    @Provides
    PreferencesSettingPresenter<PreferencesSettingView> provideMainPresenter(PreferencesSettingPresenterImpl<PreferencesSettingView> mMainPresenter) {
        return mMainPresenter;
    }
}
