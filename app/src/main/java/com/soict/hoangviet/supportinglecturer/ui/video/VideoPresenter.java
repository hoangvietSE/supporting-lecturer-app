package com.soict.hoangviet.supportinglecturer.ui.video;

import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface VideoPresenter<V extends BaseView> extends BasePresenter<V> {
    void fetchVideo(boolean isRefresh);
}
