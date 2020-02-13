package com.soict.hoangviet.supportinglecturer.ui.base;


import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    private V mBaseView;
    private final Context context;
    private final ISharePreference mSharePreference;
    private final CompositeDisposable mCompositeDisposable;

    public BasePresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        this.mSharePreference = mSharePreference;
        this.mCompositeDisposable = mCompositeDisposable;
        this.context = context;
    }

    @Override
    public void onAttach(V baseView) {
        this.mBaseView = baseView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
    }

    protected boolean isViewAttached() {
        return mBaseView != null;
    }

    protected V getView() {
        return mBaseView;
    }

    protected ISharePreference getmSharePreference() {
        return mSharePreference;
    }

    protected CompositeDisposable getmCompositeDisposable() {
        return mCompositeDisposable;
    }

    public Context getContext() {
        return context;
    }
}
