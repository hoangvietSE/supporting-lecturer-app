package com.soict.hoangviet.supportinglecturer.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.data.network.Repository;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.entity.response.FacebookResponse;
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
    private Repository repository;

    @Inject
    public HomePresenterImpl(Context context, Repository repository, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
        this.repository = repository;
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

    @Override
    public void getRtmpFacebookLive() {
        getmCompositeDisposable().add(
                repository.getRtmpFacebookLive("LIVE_NOW", AccessToken.getCurrentAccessToken().getToken())
                        .doOnSubscribe(disposable -> {
                            getView().showLoading();
                        })
                        .doFinally(() -> {
                            getView().hideLoading();
                        })
                        .subscribe(
                                response -> {
                                    if (response.getStreamUrl() != null) {
                                        getmSharePreference().setRtmpFacebook(Define.KeyPreference.RTMP_FACEBOOK, response.getStreamUrl());
                                        getView().goToTeacherScreenLiveStream();
                                    }
                                },
                                throwable -> {
                                    handleFailure(throwable);
                                }
                        )
        );
    }

    @Override
    public void getInfoFacebook() {
        //OnCompleted is invoked once the GraphRequest is successful
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
            try {
                FacebookResponse facebookResponse = new Gson().fromJson(object.toString(), FacebookResponse.class);
                getView().showInfoFacebook(facebookResponse);
            } catch (Exception e) {
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(163)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }
}
