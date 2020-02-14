package com.soict.hoangviet.supportinglecturer.ui.edit;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class EditPresenterImpl<V extends EditView> extends BasePresenterImpl<V> implements EditPresenter<V> {

    @Inject
    public EditPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }
}
