package com.soict.hoangviet.supportinglecturer.ui.main;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenterImpl<V extends MainView> extends BasePresenterImpl<V> implements MainPresenter<V> {

    @Inject
    public MainPresenterImpl(ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(mSharePreference, mCompositeDisposable);
    }
}
