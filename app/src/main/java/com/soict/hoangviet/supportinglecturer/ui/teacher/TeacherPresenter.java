package com.soict.hoangviet.supportinglecturer.ui.teacher;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.pedro.rtplibrary.rtmp.RtmpDisplay;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

import java.io.IOException;

import okhttp3.MultipartBody;

public interface TeacherPresenter<V extends BaseView> extends BasePresenter<V> {
    void showColorPicker(Context context, View view);

    void getFilePDF(MultipartBody.Part body);

    void getFilePPT(MultipartBody.Part body);

    void getFilePPTX(MultipartBody.Part body);

    void getBackgroundColor();

    void initStream(int resultCode, Intent data);

    void startStreamVideo(RtmpDisplay rtmpDisplay, int orientation, int mScreenDensity, int resultCode, Intent data);

    void onDoneSettingVideo(String pathVideo, int bitRate, int frameRate, String originName);

    void startRecordVideo(RtmpDisplay rtmpDisplay, int orientation, int mScreenDensity, int resultCodeTask, Intent dataTask);

    void startRecord(RtmpDisplay rtmpDisplay) throws IOException;

    void endYoutubeEventTask(Context context, String broadcastId);

    void onResumeRecord(String path, String name);

    void stopScreenSharing(RtmpDisplay rtmpDisplay);

    void initZoom();

    void executeStreamVideo(Context context, RtmpDisplay rtmpDisplay, int resultCode, Intent data);

    void executeRecordVideo(Context context, RtmpDisplay rtmpDisplay, int resultCode, Intent data);
}
