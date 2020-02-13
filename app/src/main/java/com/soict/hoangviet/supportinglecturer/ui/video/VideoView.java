package com.soict.hoangviet.supportinglecturer.ui.video;

import com.soict.hoangviet.supportinglecturer.entity.response.VideoResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

import java.util.List;

public interface VideoView extends BaseView {
    void showListVideo(List<VideoResponse> videoResponseList, boolean isRefresh);
}
