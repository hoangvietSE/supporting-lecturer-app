package com.soict.hoangviet.supportinglecturer.ui.prefences;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PreferencesSettingPresenterImpl<V extends PreferencesSettingView> extends BasePresenterImpl<V> implements PreferencesSettingPresenter<V> {

    @Inject
    public PreferencesSettingPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }
}
