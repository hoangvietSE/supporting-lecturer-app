package com.soict.hoangviet.supportinglecturer.ui.home;

import android.app.Activity;

import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface HomePresenter<V extends BaseView> extends BasePresenter<V> {
    void requestPermission(Activity activity);
}
