package com.soict.hoangviet.supportinglecturer.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;
import com.soict.hoangviet.supportinglecturer.utils.PermissionUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class HomePresenterImpl<V extends BaseView> extends BasePresenterImpl<V> implements HomePresenter<V> {

    @Inject
    public HomePresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

    @Override
    public void requestPermission(Activity activity) {
        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO};
        if (!PermissionUtil.hasPermission(getContext(), permissions)) {
            PermissionUtil.reuqestPermission(activity, permissions, HomeActivity.REQUEST_CODE_PERMISSION);
        }
    }
}
