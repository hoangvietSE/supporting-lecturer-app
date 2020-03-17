package com.soict.hoangviet.supportinglecturer.data.network;

import com.soict.hoangviet.supportinglecturer.entity.response.ChannelResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.FileResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.LiveVideoFacebookResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.youtube.YoutubeVideoResponse;
import com.soict.hoangviet.supportinglecturer.utils.Define;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.http.Query;

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

    public Single<LiveVideoFacebookResponse> getRtmpFacebookLive(String status, String accessToken) {
        return apiInterface.getRtmpFacebookLive(Define.Api.Url.FACEBOOK_URL, status, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ChannelResponse> getChannelYoutube(Map<String,Object> data) {
        return apiInterface.getChannelYoutube(Define.Api.Url.YOUTUBE_URL, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<YoutubeVideoResponse> getListVideoYoutube(Map<String,Object> data) {
        return apiInterface.getListVideoYoutube(Define.Api.Url.YOUTUBE_URL, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
