package com.soict.hoangviet.supportinglecturer.ui.teacher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.pedro.rtplibrary.rtmp.RtmpDisplay;
import com.soict.hoangviet.supportinglecturer.BuildConfig;
import com.soict.hoangviet.supportinglecturer.data.network.Repository;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.utils.Define;
import com.soict.hoangviet.supportinglecturer.youtube.YouTubeApi;
import com.soict.hoangviet.supportinglecturer.youtube.YouTubeNewSingleton;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import top.defaults.colorpicker.ColorPickerPopup;

public class TeacherPresenterImpl<V extends TeacherView> extends BasePresenterImpl<V> implements TeacherPresenter<V> {
    private Repository repository;
    private static final int DISPLAY_WIDTH = 1920;
    private static final int DISPLAY_HEIGHT = 1080;
    private static final String TAG = TeacherPresenterImpl.class.getSimpleName();
    private int backgroundColor = 0xFFD6E6F5;
    private String pathVideo;
    private int frameRate;
    private int bitRate;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Inject
    public TeacherPresenterImpl(Context context, Repository repository, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
        this.repository = repository;
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
        getmCompositeDisposable().add(
                repository.getFilePDF(body, BuildConfig.API_KEY)
                        .doOnSubscribe(disposable -> {
                            getView().showLoading();
                        })
                        .doFinally(() -> {

                        })
                        .subscribe(
                                response -> {
                                    getView().showFileConvert(response);
                                }, throwable -> {
                                    handleFailure(throwable);
                                    getView().hideLoading();
                                })
        );
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
            getView().executeStreamVideo(resultCode, data);
        } else {
            getView().executeRecordVideo(resultCode, data);
        }
    }

    @Override
    public void startStreamVideo(RtmpDisplay rtmpDisplay, int orientation, int mScreenDensity, int resultCode, Intent data) {
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
    public void startRecordVideo(RtmpDisplay rtmpDisplay, int orientation, int mScreenDensity, int resultCodeTask, Intent dataTask) {
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

    @SuppressLint("CheckResult")
    @Override
    public void endYoutubeEventTask(Context context, String broadcastId) {
        Observable.fromCallable(() -> {
            try {
                YouTubeApi.endEvent(YouTubeNewSingleton.newInstance(getmSharePreference().getAccountNameGoogle(Define.KeyPreference.ACCOUNT_NAME), context).getYoutube(), broadcastId);
            } catch (UserRecoverableAuthIOException userRecoverableException) {
                Log.w(TAG, "getSubscription:recoverable exception", userRecoverableException);
                ((TeacherActivity) context).startActivityForResult(userRecoverableException.getIntent(), Define.RequestCode.REQUEST_RECOVERY_ACCOUNT);
                return false;
            } catch (IOException e) {
                Log.w(TAG, "getSubscription:exception", e);
                return false;
            }
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getView().showLoading();
                })
                .doFinally(() -> {
                    getView().hideLoading();
                })
                .subscribe(
                        result -> {
                            if (result){
                            }
                        },
                        throwable -> {

                        }
                );
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
            if (getmSharePreference().getLiveStreamStatus(Define.KeyPreference.IS_LIVESTREAMED)) {
                String broadcastID = getmSharePreference().getBroadcastId(Define.KeyPreference.BROADCAST_ID);
                getView().endYoutubeEventTask(broadcastID);
                getmSharePreference().setLiveStreamStatus(Define.KeyPreference.IS_LIVESTREAMED, false);
                getmSharePreference().setRtmpGoogle(Define.KeyPreference.RTMP_GOOGLE, "");
                getmSharePreference().setBroadcastId(Define.KeyPreference.BROADCAST_ID, "");
            }

        }
    }

    @Override
    public void initZoom() {
        getView().setZoom(getmSharePreference().getSettingZoomCheckedItem(Define.KeyPreference.SETTING_ZOOM));
    }

    @SuppressLint("CheckResult")
    @Override
    public void executeStreamVideo(Context context, RtmpDisplay rtmpDisplay, int resultCode, Intent data) {
        Observable.fromCallable(() -> {
            int currentOrientation = context.getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((TeacherActivity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                ((TeacherActivity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }

            int rotation = ((TeacherActivity) context).getWindowManager().getDefaultDisplay().getRotation();
            int orientation = ORIENTATIONS.get(rotation + 90);
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int mScreenDensity = (int) (metrics.density * 160f);
            startStreamVideo(rtmpDisplay, orientation, mScreenDensity, resultCode, data);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getView().preExecuteVideo();
                })
                .doFinally(() -> {
                    getView().hideLoading();
                })
                .subscribe(
                        result -> {
                            if (true){
                                getView().postExecuteStreamVideo();
                            }
                        },
                        throwable -> {

                        }
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void executeRecordVideo(Context context, RtmpDisplay rtmpDisplay, int resultCode, Intent data) {
        Observable.fromCallable(() -> {
            int currentOrientation = context.getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((TeacherActivity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                ((TeacherActivity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }

            int rotation = ((TeacherActivity) context).getWindowManager().getDefaultDisplay().getRotation();
            int orientation = ORIENTATIONS.get(rotation + 90);
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int mScreenDensity = (int) (metrics.density * 160f);
            startRecordVideo(rtmpDisplay, orientation, mScreenDensity, resultCode, data);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    getView().preExecuteVideo();
                })
                .doFinally(() -> {
                    getView().hideLoading();
                })
                .subscribe(
                        result -> {
                            if (result) {
                                getView().postExecuteRecordVideo();
                            }
                        },
                        throwable -> {
                        }
                );
    }
}
