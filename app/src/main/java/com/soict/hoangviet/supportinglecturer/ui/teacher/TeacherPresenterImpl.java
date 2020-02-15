package com.soict.hoangviet.supportinglecturer.ui.teacher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.pedro.rtplibrary.rtmp.RtmpDisplay;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;
import com.soict.hoangviet.supportinglecturer.utils.Define;
import com.soict.hoangviet.supportinglecturer.utils.RecordUtil;
import com.soict.hoangviet.supportinglecturer.youtube.YouTubeApi;
import com.soict.hoangviet.supportinglecturer.youtube.YouTubeNewSingleton;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MultipartBody;
import top.defaults.colorpicker.ColorPickerPopup;

public class TeacherPresenterImpl<V extends TeacherView> extends BasePresenterImpl<V> implements TeacherPresenter<V> {
    private static final int DISPLAY_WIDTH = 1920;
    private static final int DISPLAY_HEIGHT = 1080;
    private static final String TAG = TeacherPresenterImpl.class.getSimpleName();
    private int backgroundColor = 0xFFD6E6F5;
    private String pathVideo;
    private int frameRate;
    private int bitRate;

    @Inject
    public TeacherPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

    @Override
    public void showColorPicker(Context context, View view) {
        new ColorPickerPopup.Builder(context)
                .initialColor(Color.RED) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Đồng ý")
                .cancelTitle("Hủy bỏ")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(view, new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        backgroundColor = color;
                        getView().setBackgroundColor(backgroundColor);
                    }
                });
    }

    @Override
    public void getFilePDF(MultipartBody.Part body) {

    }

    @Override
    public void getFilePPT(MultipartBody.Part body) {

    }

    @Override
    public void getFilePPTX(MultipartBody.Part body) {

    }

    @Override
    public void getBackgroundColor() {
        getView().setBackgroundColor(backgroundColor);
    }

    @Override
    public void initStream(int resultCode, Intent data) {
        if (!getmSharePreference().getRtmpFacebook(Define.KeyPreference.RTMP_FACEBOOK).isEmpty()
                || !getmSharePreference().getRtmpGoogle(Define.KeyPreference.RTMP_GOOGLE).isEmpty()) {
            getView().executeSlowInitVideo(resultCode, data);
        } else {
            getView().executeSlowInitRecordVideo(resultCode, data);
        }
    }

    @Override
    public void startStreamSlowInitVideo(RtmpDisplay rtmpDisplay, int orientation, int mScreenDensity, int resultCode, Intent data) {
        if (getmSharePreference().getLoginStatusFromFacebook(Define.KeyPreference.LOGIN_FROM_FACEBOOK)) {
            // TODO RTMP FACEBOOK
            if (rtmpDisplay.prepareAudio() && rtmpDisplay.prepareVideo(DISPLAY_WIDTH, DISPLAY_HEIGHT, frameRate, bitRate, orientation, mScreenDensity)) {
                rtmpDisplay.setIntentResult(resultCode, data);
                rtmpDisplay.startStream(getmSharePreference().getRtmpFacebook(Define.KeyPreference.RTMP_FACEBOOK));
            }
        } else {
            // TODO RTMP YOUTUBE
            if (rtmpDisplay.prepareAudio() && rtmpDisplay.prepareVideo(DISPLAY_WIDTH, DISPLAY_HEIGHT, frameRate, bitRate, orientation, mScreenDensity)) {
                rtmpDisplay.setIntentResult(resultCode, data);
                rtmpDisplay.startStream(getmSharePreference().getRtmpFacebook(Define.KeyPreference.RTMP_GOOGLE));

            }
        }
    }

    @Override
    public void onDoneSettingVideo(String pathVideo, int bitRate, int frameRate, String originName) {
        this.pathVideo = pathVideo;
        this.bitRate = bitRate;
        this.frameRate = frameRate;
        if (getmSharePreference().getLoginStatus(Define.KeyPreference.IS_LOGINED)) {
            getView().startRtmpDisplay(true, originName);
        } else {
            getView().startRtmpDisplay(false, originName);
        }
    }

    @Override
    public void startStreamSlowInitRecordVideo(RtmpDisplay rtmpDisplay, int orientation, int mScreenDensity, int resultCodeTask, Intent dataTask) {
        if (rtmpDisplay.prepareAudio() && rtmpDisplay.prepareVideo(DISPLAY_WIDTH, DISPLAY_HEIGHT, frameRate, bitRate, orientation, mScreenDensity)) {
            rtmpDisplay.setIntentResult(resultCodeTask, dataTask);
            try {
                rtmpDisplay.startRecord(pathVideo);
            } catch (IOException e) {
                rtmpDisplay.stopRecord();
            }
        }
    }

    @Override
    public void startRecord(RtmpDisplay rtmpDisplay) throws IOException {
        rtmpDisplay.startRecord(pathVideo);
    }

    @Override
    public void endEventTask(Context context, String[] params) {
        try {
            if (params.length >= 1) {
                YouTubeApi.endEvent(YouTubeNewSingleton.newInstance(getmSharePreference().getAccountNameGoogle(Define.KeyPreference.ACCOUNT_NAME), context).getYoutube(), params[0]);
            }
        } catch (UserRecoverableAuthIOException userRecoverableException) {
            Log.w(TAG, "getSubscription:recoverable exception", userRecoverableException);
            ((TeacherActivity) context).startActivityForResult(userRecoverableException.getIntent(), Define.RequestCode.REQUEST_RECOVERY_ACCOUNT);
        } catch (IOException e) {
            Log.w(TAG, "getSubscription:exception", e);
        }
    }

    @Override
    public void onResumeRecord(String path, String name) {
        this.pathVideo = path;
        if (getmSharePreference().getLoginStatus(Define.KeyPreference.IS_LOGINED)) {
            getView().startRtmpDisplay(true, name);
        } else {
            getView().startRtmpDisplay(false, name);
        }
    }

    @Override
    public void stopScreenSharing(RtmpDisplay rtmpDisplay) {
        if (rtmpDisplay.isRecording()) {
            rtmpDisplay.stopRecord();
            getView().setImageRecordStop();
        }
        if (rtmpDisplay.isStreaming()) rtmpDisplay.stopStream();
        if (getmSharePreference().getLoginStatusFromFacebook(Define.KeyPreference.LOGIN_FROM_FACEBOOK)) {
            //  TODO STOP LIVE Facebook
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + getmSharePreference().getUserId(Define.KeyPreference.USER_ID) + "?end_live_video=true",
                    null,
                    HttpMethod.POST,
                    responseStream -> {
                        Log.d(TAG, "stopScreenSharing: " + responseStream.toString());
                    }
            ).executeAsync();
            getmSharePreference().setRtmpFacebook(Define.KeyPreference.RTMP_FACEBOOK, "");
        } else {
            //  TODO STOP LIVE YouTube
            if (getmSharePreference().getLiveStreamStatus(Define.KeyPreference.IS_LIVESTREAMED)) {
                String broadcastID = getmSharePreference().getBroadcastId(Define.KeyPreference.BROADCAST_ID);
                getView().endEventTask(broadcastID);
                getmSharePreference().setLiveStreamStatus(Define.KeyPreference.IS_LIVESTREAMED, false);
                getmSharePreference().setRtmpGoogle(Define.KeyPreference.RTMP_GOOGLE, "");
                getmSharePreference().setBroadcastId(Define.KeyPreference.BROADCAST_ID, "");
            }

        }
    }


}
