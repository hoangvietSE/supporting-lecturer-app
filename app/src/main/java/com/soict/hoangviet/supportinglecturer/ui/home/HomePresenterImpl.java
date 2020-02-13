package com.soict.hoangviet.supportinglecturer.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;
import com.soict.hoangviet.supportinglecturer.ui.login.LoginActivity;
import com.soict.hoangviet.supportinglecturer.ui.login.LoginView;
import com.soict.hoangviet.supportinglecturer.utils.Define;
import com.soict.hoangviet.supportinglecturer.utils.NetworkUtil;
import com.soict.hoangviet.supportinglecturer.utils.PermissionUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class HomePresenterImpl<V extends HomeView> extends BasePresenterImpl<V> implements HomePresenter<V> {

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

    @Override
    public void checkConnectedToNetwork(Context context) {
        if (!NetworkUtil.isConnectedToNetwork(context)) {
            getmSharePreference().setLoginStatus(Define.KeyPreference.IS_LOGINED, false);
            if (getmSharePreference().getLoginStatusFromFacebook(Define.KeyPreference.LOGIN_FROM_FACEBOOK)) {
                getmSharePreference().setLoginStatusFromFacebook(Define.KeyPreference.LOGIN_FROM_FACEBOOK, false);
                getmSharePreference().setRtmpFacebook(Define.KeyPreference.RTMP_FACEBOOK, "");
                getmSharePreference().setUserId(Define.KeyPreference.USER_ID, "");
                LoginManager.getInstance().logOut();
            } else {
                getmSharePreference().setLoginStatusFromGoogle(Define.KeyPreference.LOGIN_FROM_GOOGLE, false);
                getmSharePreference().setAccountNameGoogle(Define.KeyPreference.ACCOUNT_NAME, "");
                getmSharePreference().setRtmpGoogle(Define.KeyPreference.RTMP_GOOGLE, "");
            }
        }
    }

    @Override
    public void onLoginButtonClick() {
        if (!getmSharePreference().getLoginStatus(Define.KeyPreference.IS_LOGINED)) {
            getView().goToLoginScreen();
        } else {
            getView().showConfirmLogout();
        }
    }

    @Override
    public void logoutFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            getmSharePreference().setLoginStatusFromFacebook(Define.KeyPreference.LOGIN_FROM_FACEBOOK, false);
            getmSharePreference().setRtmpFacebook(Define.KeyPreference.RTMP_FACEBOOK, "");
            getmSharePreference().setUserId(Define.KeyPreference.USER_ID, "");
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    public void logoutGoogle() {
        getmSharePreference().setLoginStatusFromGoogle(Define.KeyPreference.LOGIN_FROM_GOOGLE, false);
        getmSharePreference().setAccountNameGoogle(Define.KeyPreference.ACCOUNT_NAME, "");
        getmSharePreference().setRtmpGoogle(Define.KeyPreference.RTMP_GOOGLE, "");
    }
}
