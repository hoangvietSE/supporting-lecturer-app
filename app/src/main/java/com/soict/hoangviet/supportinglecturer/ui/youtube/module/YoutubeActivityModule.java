package com.soict.hoangviet.supportinglecturer.ui.youtube.module;

import com.soict.hoangviet.supportinglecturer.ui.youtube.YoutubePresenter;
import com.soict.hoangviet.supportinglecturer.ui.youtube.YoutubePresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.youtube.YoutubeView;

import dagger.Module;
import dagger.Provides;

@Module
public class YoutubeActivityModule {

    @Provides
    YoutubePresenter<YoutubeView> provideMainPresenter(YoutubePresenterImpl<YoutubeView> mMainPresenter) {
        return mMainPresenter;
    }
}
