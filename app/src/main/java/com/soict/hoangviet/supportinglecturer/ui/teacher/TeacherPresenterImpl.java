package com.soict.hoangviet.supportinglecturer.ui.teacher;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TeacherPresenterImpl<V extends BaseView> extends BasePresenterImpl<V> implements TeacherPresenter<V> {

    @Inject
    public TeacherPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

}
