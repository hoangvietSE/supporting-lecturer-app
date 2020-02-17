package com.soict.hoangviet.supportinglecturer.data.network;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.utils.DeviceUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {
    private Context mContext;

    public NetworkConnectionInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (DeviceUtil.hasConnection(mContext)) {
            Request.Builder builder = chain.request().newBuilder();
            return chain.proceed(builder.build());
        }
        throw new NoConnectivityException();
    }

    public class NoConnectivityException extends IOException {
        @Override
        public String getMessage() {
            return ApiConstant.HttpMessage.ERROR_NETWORK;
        }
    }
}
