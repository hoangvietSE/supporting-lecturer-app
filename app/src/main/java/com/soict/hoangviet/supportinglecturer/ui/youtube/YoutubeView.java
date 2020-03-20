package com.soict.hoangviet.supportinglecturer.ui.youtube;

import com.soict.hoangviet.supportinglecturer.entity.response.youtube.YoutubeVideoResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface YoutubeView extends BaseView {
    void showListVideoYoutube(YoutubeVideoResponse response, boolean isRefresh);
}
