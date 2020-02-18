package com.soict.hoangviet.supportinglecturer.ui.language.module;

import com.soict.hoangviet.supportinglecturer.ui.language.LanguagePresenter;
import com.soict.hoangviet.supportinglecturer.ui.language.LanguagePresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.language.LanguageView;

import dagger.Module;
import dagger.Provides;

@Module
public class LanguageActivityModule {

    @Provides
    LanguagePresenter<LanguageView> provideMainPresenter(LanguagePresenterImpl<LanguageView> mMainPresenter) {
        return mMainPresenter;
    }
}
