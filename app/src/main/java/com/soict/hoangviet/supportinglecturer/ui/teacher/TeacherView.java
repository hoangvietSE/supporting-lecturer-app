package com.soict.hoangviet.supportinglecturer.ui.teacher;

import android.content.Intent;

import com.soict.hoangviet.supportinglecturer.entity.response.FileResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface TeacherView extends BaseView {
    void setBackgroundColor(int backgroundColor);

    void executeStreamVideo(int resultCode, Intent data);

    void executeRecordVideo(int resultCode, Intent data);

    void startRtmpDisplay(boolean b, String originName);

    void setImageRecordStop();

    void endYoutubeEventTask(String broadcastID);

    void showFileConvert(FileResponse response);

    void setZoom(int settingZoomCheckedItem);

    void preExecuteVideo();

    void postExecuteStreamVideo();

    void postExecuteRecordVideo();

    void onNetworkConnection(boolean isConnected);
}
