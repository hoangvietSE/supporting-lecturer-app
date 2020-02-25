package com.soict.hoangviet.supportinglecturer.ui.prefences;

import android.content.Context;
import android.content.SharedPreferences;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.utils.Define;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PreferencesSettingPresenterImpl<V extends PreferencesSettingView> extends BasePresenterImpl<V> implements PreferencesSettingPresenter<V> {

    @Inject
    public PreferencesSettingPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

    @Override
    public void setPreviousSelectedAsSummary(SharedPreferences prefs) {
        String videoResolution = prefs.getString(Define.KeyPreference.VIDEO_RESOLUTION, "");
        getView().setVideoResolution(videoResolution);
        boolean audioEnabled = prefs.getBoolean(Define.KeyPreference.RECORD_AUDIO, true);
        getView().setAudioEnable(audioEnabled);
        String audioSource = prefs.getString(Define.KeyPreference.AUDIO_SOURCE, "");
        getView().setAudioSource(audioSource);
        String videoEncoder = prefs.getString(Define.KeyPreference.VIDEO_ENCODER, "");
        getView().setVideoEncoder(videoEncoder);
        String videoFrameRate = prefs.getString(Define.KeyPreference.VIDEO_FPS, "");
        getView().setVideoFrameRate(videoFrameRate);
        String videoBitRate = prefs.getString(Define.KeyPreference.VIDEO_BITRATE, "");
        getView().setVideoBitRate(videoBitRate);
        String outputFormat = prefs.getString(Define.KeyPreference.OUTPUT_FORMAT, "");
        getView().setOutputFormat(outputFormat);
        boolean showNotification = prefs.getBoolean(Define.KeyPreference.SHOW_NOTIFICATION, true);
        getView().setShowNotification(showNotification);
    }
}
