package com.soict.hoangviet.supportinglecturer.ui.home;

import android.app.Activity;
import android.content.Context;

import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface HomePresenter<V extends BaseView> extends BasePresenter<V> {
    void requestPermission(Activity activity);

    void checkConnectedToNetwork(Context context);

    void onLoginButtonClick();

    void logoutFacebook();

    void logoutGoogle();

    void getRtmpFacebookLive();
}
