package com.soict.hoangviet.supportinglecturer.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.data.network.Repository;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.entity.response.ChannelResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.FacebookResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.youtube.YoutubeVideoResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.utils.Define;
import com.soict.hoangviet.supportinglecturer.utils.NetworkUtil;
import com.soict.hoangviet.supportinglecturer.utils.PermissionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        if (!PermissionUtil.hasPermission(permissions)) {
            PermissionUtil.requestPermission(activity, permissions, HomeActivity.REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void checkConnectedToNetwork(Context context) {
        if (!NetworkUtil.isConnectedToNetwork(context)) {
            getmSharePreference().setLoginStatus(false);
            if (getmSharePreference().getLoginStatusFromFacebook()) {
                getmSharePreference().setLoginStatusFromFacebook(false);
                getmSharePreference().setRtmpFacebook("");
                getmSharePreference().setUserId("");
                LoginManager.getInstance().logOut();
            } else {
                getmSharePreference().setLoginStatusFromGoogle(false);
                getmSharePreference().setAccountNameGoogle("");
                getmSharePreference().setRtmpGoogle("");
            }
        }
    }

    @Override
    public void onLoginButtonClick() {
        if (!getmSharePreference().getLoginStatus()) {
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
            getmSharePreference().setLoginStatusFromFacebook(false);
            getmSharePreference().setRtmpFacebook("");
            getmSharePreference().setUserId("");
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    public void logoutGoogle() {
        getmSharePreference().setLoginStatusFromGoogle(false);
        getmSharePreference().setAccountNameGoogle("");
        getmSharePreference().setRtmpGoogle("");
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
                                        getmSharePreference().setRtmpFacebook(response.getStreamUrl());
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

    @Override
    public void loopIndicator() {
        getmCompositeDisposable().add(
                repository.getHomeObservable()
                        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    getView().loopViewPager();
                }, throwable -> {

                })
        );
    }
}
