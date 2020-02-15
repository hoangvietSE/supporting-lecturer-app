package com.soict.hoangviet.supportinglecturer.data.network;

import com.soict.hoangviet.supportinglecturer.entity.response.FileResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class Repository {

    private ApiInterface apiInterface;

    public Repository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Single<FileResponse> getFilePDF(MultipartBody.Part file, String keySecret) {
        return apiInterface.uploadFilePDF(file, keySecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<FileResponse> getFilePPT(MultipartBody.Part file, String keySecret) {
        return apiInterface.uploadFilePPT(file, keySecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<FileResponse> getFilePPTX(MultipartBody.Part file, String keySecret) {
        return apiInterface.uploadFilePPTX(file, keySecret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
