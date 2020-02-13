package com.soict.hoangviet.supportinglecturer.ui.splash;

import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface SplashPresenter<V extends BaseView> extends BasePresenter<V> {
    void checkNetworkConnection();
}
