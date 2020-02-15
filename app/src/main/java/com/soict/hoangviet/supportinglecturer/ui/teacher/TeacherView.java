package com.soict.hoangviet.supportinglecturer.ui.teacher;

import android.content.Intent;

import com.soict.hoangviet.supportinglecturer.entity.response.FileResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface TeacherView extends BaseView {
    void setBackgroundColor(int backgroundColor);

    void executeSlowInitVideo(int resultCode, Intent data);

    void executeSlowInitRecordVideo(int resultCode, Intent data);

    void startRtmpDisplay(boolean b, String originName);

    void setImageRecordStop();

    void endEventTask(String broadcastID);

    void showFileConvert(FileResponse response);
}
