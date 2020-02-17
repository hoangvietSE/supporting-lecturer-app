package com.soict.hoangviet.supportinglecturer.ui.base;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.soict.hoangviet.supportinglecturer.data.network.ApiConstant;
import com.soict.hoangviet.supportinglecturer.data.network.ApiError;
import com.soict.hoangviet.supportinglecturer.data.network.ApiException;
import com.soict.hoangviet.supportinglecturer.data.network.ErrorResponse;
import com.soict.hoangviet.supportinglecturer.data.network.NetworkConnectionInterceptor;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.utils.ToastUtil;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

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

    protected void handleFailure(Throwable throwable) {
        ApiError apiError;
        if (throwable instanceof NetworkConnectionInterceptor.NoConnectivityException) {
            apiError = new ApiError(throwable.getMessage());
        } else {
            if (throwable instanceof HttpException) {
                try {
                    String errorBody = ((HttpException) throwable).response().errorBody().toString();
                    ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);
                    apiError = new ApiError(errorResponse.getError().getMessage().getName().get(0));
                } catch (JsonParseException jsonParseException) {
                    apiError = new ApiError();
                } catch (IllegalStateException e) {
                    apiError = new ApiError();
                }

            } else if (throwable instanceof TimeoutException ||
                    throwable instanceof SocketException ||
                    throwable instanceof UnknownHostException) {
                apiError = new ApiError(ApiConstant.HttpMessage.TIME_OUT);
            } else {
                apiError = new ApiError();
            }
        }
        ApiException apiException = apiError.getApiException();
        ToastUtil.show(context, apiException.getMessage());
    }
}
