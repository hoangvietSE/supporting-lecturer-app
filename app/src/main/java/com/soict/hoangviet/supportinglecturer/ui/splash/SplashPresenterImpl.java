package com.soict.hoangviet.supportinglecturer.ui.splash;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;
import com.soict.hoangviet.supportinglecturer.utils.NetworkUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SplashPresenterImpl<V extends SplashView> extends BasePresenterImpl<V> implements SplashPresenter<V> {

    @Inject
    public SplashPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

    @Override
    public void checkNetworkConnection() {
        if (NetworkUtil.isConnectedToNetwork(getContext())) {
            getView().onNetworkConnection(true);
        } else {
            getView().onNetworkConnection(false);
        }
    }
}
