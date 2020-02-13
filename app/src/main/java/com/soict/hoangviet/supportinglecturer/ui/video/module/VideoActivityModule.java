package com.soict.hoangviet.supportinglecturer.ui.video.module;

import com.soict.hoangviet.supportinglecturer.ui.video.VideoPresenter;
import com.soict.hoangviet.supportinglecturer.ui.video.VideoPresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.video.VideoView;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoActivityModule {

    @Provides
    VideoPresenter<VideoView> provideMainPresenter(VideoPresenterImpl<VideoView> mMainPresenter) {
        return mMainPresenter;
    }
}
