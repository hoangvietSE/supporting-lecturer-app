package com.soict.hoangviet.supportinglecturer.ui.youtube;

import com.soict.hoangviet.supportinglecturer.entity.response.youtube.ItemsItem;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface YoutubePresenter<V extends BaseView> extends BasePresenter<V> {
    void fetchListVideoYoutube();
}
