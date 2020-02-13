package com.soict.hoangviet.supportinglecturer.ui.login;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenterImpl<V extends BaseView> extends BasePresenterImpl<V> implements LoginPresenter<V> {

    @Inject
    public LoginPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

}
