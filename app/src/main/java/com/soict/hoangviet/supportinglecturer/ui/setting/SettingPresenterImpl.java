package com.soict.hoangviet.supportinglecturer.ui.setting;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SettingPresenterImpl<V extends SettingView> extends BasePresenterImpl<V> implements SettingPresenter<V> {

    @Inject
    public SettingPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }
}
