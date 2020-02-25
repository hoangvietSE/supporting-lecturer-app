package com.soict.hoangviet.supportinglecturer.ui.prefences;

import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface PreferencesSettingView extends BaseView {
    void setVideoResolution(String videoResolution);

    void setAudioEnable(boolean audioEnabled);

    void setAudioSource(String audioSource);

    void setVideoEncoder(String videoEncoder);

    void setVideoFrameRate(String videoFrameRate);

    void setVideoBitRate(String videoBitRate);

    void setOutputFormat(String outputFormat);

    void setShowNotification(boolean showNotification);
}
