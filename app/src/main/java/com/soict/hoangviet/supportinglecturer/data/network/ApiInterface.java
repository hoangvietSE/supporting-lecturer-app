package com.soict.hoangviet.supportinglecturer.data.network;

import com.soict.hoangviet.supportinglecturer.entity.response.ChannelResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.FileResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.LiveVideoFacebookResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.youtube.YoutubeVideoResponse;
import com.soict.hoangviet.supportinglecturer.utils.Define;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {
    @Multipart
    @POST("pdf/to/png")
    Single<FileResponse> uploadFilePDF(@Part MultipartBody.Part file, @Query(Define.Api.Query.SECRET) String keySecret);

    @Multipart
    @POST("ppt/to/png")
    Single<FileResponse> uploadFilePPT(@Part MultipartBody.Part file, @Query(Define.Api.Query.SECRET) String keySecret);

    @Multipart
    @POST("pptx/to/png")
    Single<FileResponse> uploadFilePPTX(@Part MultipartBody.Part file, @Query(Define.Api.Query.SECRET) String keySecret);

    @POST
    @Headers("Content-Type: application/json")
    Single<LiveVideoFacebookResponse> getRtmpFacebookLive(
            @Url String url,
            @Query("status") String status,
            @Query("access_token") String accessToken
    );

    @GET
    @Headers("Content-Type: application/json")
    Single<ChannelResponse> getChannelYoutube(
            @Url String url,
            @QueryMap Map<String, Object> data
    );

    @GET
    @Headers("Content-Type: application/json")
    Single<YoutubeVideoResponse> getListVideoYoutube(
            @Url String url,
            @QueryMap Map<String, Object> data
    );
}
