package com.soict.hoangviet.supportinglecturer.data.network;

import com.soict.hoangviet.supportinglecturer.entity.response.FileResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("pdf/to/png?Secret=iEBPvNXpjZtILiTi")
    Single<FileResponse> uploadFilePDF(@Part MultipartBody.Part file);

    @Multipart
    @POST("ppt/to/png?Secret=iEBPvNXpjZtILiTi")
    Single<FileResponse> uploadFilePPT(@Part MultipartBody.Part file);

    @Multipart
    @POST("pptx/to/png?Secret=iEBPvNXpjZtILiTi")
    Single<FileResponse> uploadFilePPTX(@Part MultipartBody.Part file);
}
