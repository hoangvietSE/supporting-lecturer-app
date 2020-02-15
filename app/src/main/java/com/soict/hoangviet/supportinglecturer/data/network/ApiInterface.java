package com.soict.hoangviet.supportinglecturer.data.network;

import com.soict.hoangviet.supportinglecturer.entity.response.FileResponse;
import com.soict.hoangviet.supportinglecturer.utils.Define;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
}
