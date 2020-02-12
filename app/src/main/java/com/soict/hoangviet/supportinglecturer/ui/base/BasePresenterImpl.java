package com.soict.hoangviet.supportinglecturer.ui.base;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    private V mBaseView;
    private final ISharePreference mSharePreference;
    private final CompositeDisposable mCompositeDisposable;

    public BasePresenterImpl(ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        this.mSharePreference = mSharePreference;
        this.mCompositeDisposable = mCompositeDisposable;
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
}
