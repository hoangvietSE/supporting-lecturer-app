package com.soict.hoangviet.supportinglecturer.ui.teacher;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.pedro.rtplibrary.rtmp.RtmpDisplay;
import com.samsung.android.sdk.pen.SpenSettingViewInterface;
import com.samsung.android.sdk.pen.document.SpenObjectImage;
import com.samsung.android.sdk.pen.document.SpenPageDoc;
import com.samsung.android.sdk.pen.engine.SpenSimpleSurfaceView;
import com.samsung.android.sdk.pen.engine.SpenSurfaceView;
import com.samsung.android.sdk.pen.settingui.SpenSettingEraserLayout;
import com.samsung.android.sdk.pen.settingui.SpenSettingPenLayout;
import com.samsung.android.sdk.pen.settingui.SpenSettingTextLayout;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.customview.MovableFloatingActionButton;
import com.soict.hoangviet.supportinglecturer.customview.settime.SettingTimeTempBushDFragment;
import com.soict.hoangviet.supportinglecturer.customview.settingvideo.SettingVideoDFragment;
import com.soict.hoangviet.supportinglecturer.entity.response.FileResponse;
import com.soict.hoangviet.supportinglecturer.eventbus.RecordSuccessEvent;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseSamsungSpenSdkActivity;
import com.soict.hoangviet.supportinglecturer.ui.setting.SettingActivity;
import com.soict.hoangviet.supportinglecturer.utils.DeviceUtil;
import com.soict.hoangviet.supportinglecturer.utils.DialogUtil;
import com.soict.hoangviet.supportinglecturer.utils.FileUtil;
import com.soict.hoangviet.supportinglecturer.utils.RecordUtil;
import com.soict.hoangviet.supportinglecturer.utils.ToastUtil;

import net.ossrs.rtmp.ConnectCheckerRtmp;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class TeacherActivity extends BaseSamsungSpenSdkActivity implements TeacherView, SettingVideoDFragment.OnClickSettingVideo, ConnectCheckerRtmp, SettingTimeTempBushDFragment.OnClickSettingTime, View.OnTouchListener {
    @Inject
    TeacherPresenter<TeacherView> mPresenter;
    private static final String TAG = TeacherActivity.class.getSimpleName();
    @BindView(R.id.ivPen)
    ImageButton ibBrush;
    @BindView(R.id.ibTempBrush)
    ImageButton ibTempBrush;
    @BindView(R.id.ivEraser)
    ImageButton ibEraser;
    @BindView(R.id.ivAddImage)
    ImageButton ivAddImage;
    @BindView(R.id.ibInsertImage)
    ImageButton ibInsertImage;
    @BindView(R.id.ibInsertFile)
    ImageButton ibInsertFile;
    @BindView(R.id.ibAddVideo)
    ImageButton ibAddCamera;
    @BindView(R.id.ibCaptureScreen)
    ImageButton ibCaptureScreen;
    @BindView(R.id.ibText)
    ImageButton ibAddText;
    @BindView(R.id.ibSelection)
    ImageButton ibSelection;
    @BindView(R.id.ibBound)
    ImageButton ibRecognizeShape;
    @BindView(R.id.ibAddPage)
    ImageButton ibAddPage;
    @BindView(R.id.ibRecord)
    ImageButton ibRecord;
    @BindView(R.id.ibSave)
    ImageButton ibSave;
    @BindView(R.id.llMenuMore)
    LinearLayout llMenuMore;
    @BindView(R.id.mfa_top_down)
    MovableFloatingActionButton mfaTopDown;
    @BindView(R.id.drawView)
    RelativeLayout drawView;
    @BindView(R.id.simpleChronometer)
    Chronometer chronometer;
    @BindView(R.id.imv_setting)
    ImageView imvSetting;
    //LANDSCAPE
    @Nullable
    @BindView(R.id.mfa_left_right)
    MovableFloatingActionButton mfaLeftRight;
    @Nullable
    @BindView(R.id.rl_camera)
    RelativeLayout rlCamera;
    private int mToolType = SpenSurfaceView.TOOL_SPEN;
    private long onTimeRecord = -1;
    private static final int REQUEST_CODE_SELECT_IMAGE_BACKGROUND = 99;
    private static final int REQUEST_CODE_SELECT_IMAGE = 98;
    private static final int REQUEST_CODE_RECORD = 94;
    private static final int REQUEST_CODE_STREAM = 95;
    private static final int IS_CLOSED_RECORD = 0;
    private static final int IS_RECORDING = 1;
    //Check state orientation of output image
    private static final long MIN_TIME_RECORD = 6000L;
    private boolean isSaveRecord = true;
    private ArrayList<String> listRecordsPath = new ArrayList<>();
    private ArrayList<String> listRecordsName = new ArrayList<>();
    private Boolean runningChronometer = false;
    private Long pauseOffset = 0L;
    private int mScreenDensity;
    private static RtmpDisplay rtmpDisplay;
    private int recordStatus = 0;
    private boolean checkSessionRecord = false;
    private boolean isShowCamera = true;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_teacher;
    }

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
        super.initView();
        keepScreenAlwayOn();
        initMedia();
    }

    @Override
    protected void initZoom() {
        mPresenter.initZoom();
    }

    private void onAttachPresenter() {
        mPresenter.onAttach(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void initMedia() {
        rtmpDisplay = getInstanceRtmp();
    }

    private RtmpDisplay getInstanceRtmp() {
        if (rtmpDisplay == null) {
            return new RtmpDisplay(this, false, this);
        } else {
            return rtmpDisplay;
        }
    }

    private void keepScreenAlwayOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ibBrush.setOnClickListener(view -> {
//            mPenSurfaceView.closeControl();
            mPenSurfaceView.stopTemporaryStroke();
            mPenSurfaceView.setTouchListener(touchListenerBrush());
            if (mPenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_STROKE) {
                ibBrush.setSelected(true);
                ibTempBrush.setSelected(false);
                if (mPenSettingView.isShown()) {
                    mPenSettingView.setVisibility(View.GONE);
                } else {
                    //noinspection deprecation
                    mPenSettingView.setViewMode(SpenSettingPenLayout.VIEW_MODE_EXTENSION);
                    mPenSettingView.setVisibility(View.VISIBLE);
                }
            } else {
                selectButton(ibBrush);
                mMode = MODE_PEN;
                mPenSurfaceView.setToolTypeAction(mToolType, SpenSettingViewInterface.ACTION_STROKE);
            }
        });
        ibTempBrush.setOnClickListener(view -> {
            mPenSurfaceView.startTemporaryStroke();
            if (!isShowSetTime) {
                SettingTimeTempBushDFragment.newInstance().show(getSupportFragmentManager(), SettingTimeTempBushDFragment.class.getSimpleName());
            } else {
                mPenSurfaceView.setTouchListener(touchListenerTemporaryBrush());
                isShowSetTime = false;
            }
            mMode = MODE_PEN;
            selectButton(ibTempBrush);
            mPenSurfaceView.setToolTypeAction(mToolType, SpenSettingViewInterface.ACTION_STROKE);
//            }
        });
        ibEraser.setOnClickListener(view -> {
            // If it is in eraser tool mode.
            if (mPenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_ERASER) {
                // If EraserSettingView is displayed, close it.
                if (mEraserSettingView.isShown()) {
                    mEraserSettingView.setVisibility(View.GONE);
                    // If EraserSettingView is not displayed, display it.
                } else {
                    //noinspection deprecation
                    mEraserSettingView.setViewMode(SpenSettingEraserLayout.VIEW_MODE_NORMAL);
                    mEraserSettingView.setVisibility(View.VISIBLE);
                }
                // If it is not in eraser tool mode, change it to eraser tool mode.
            } else {
                selectButton(ibEraser);
                mPenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_ERASER);
            }
        });
        ivAddImage.setOnClickListener(view -> {
            closeSettingView();
            mPresenter.showColorPicker(this, view);
        });
        ibInsertImage.setOnClickListener(view -> {
            closeSettingView();
            callGalleryForInputImage(REQUEST_CODE_SELECT_IMAGE);
        });
        ibAddCamera.setOnClickListener(view -> {
            ibAddCamera.setClickable(false);
            mPenSurfaceView.closeControl();
            createObjectRuntime();
        });
        ibCaptureScreen.setOnClickListener(view -> {
            closeSettingView();
//            capturePenSurfaceView();
        });
        ibAddText.setOnClickListener(view -> {
            mPenSurfaceView.closeControl();
            mPenSurfaceView.stopTemporaryStroke();
            mPenSurfaceView.setTouchListener(touchListenerBrush());
            // When Pen is in text mode.
            if (mPenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_TEXT) {
                // Close TextSettingView if TextSettingView is displayed.
                if (mTextSettingView.isShown()) {
                    mTextSettingView.setVisibility(View.GONE);
                    // Display TextSettingView if TextSettingView is not displayed.
                } else {
                    //noinspection deprecation
                    mTextSettingView.setViewMode(SpenSettingTextLayout.VIEW_MODE_NORMAL);
                    mTextSettingView.setVisibility(View.VISIBLE);
                }
                // Switch to text mode unless Pen is in text mode.
            } else {
                mMode = MODE_TEXT_OBJ;
                selectButton(ibAddText);
                mPenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_TEXT);
            }
        });
        ibSelection.setOnClickListener(view -> {
            selectButton(ibSelection);
            mPenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_SELECTION);
        });
        ibRecognizeShape.setOnClickListener(view -> {
            mMode = MODE_PEN;
            selectButton(ibRecognizeShape);
            mPenSurfaceView.closeControl();
            mPenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_RECOGNITION);
        });
        ibInsertFile.setOnClickListener(view -> {
            mPenSurfaceView.closeControl();
            closeSettingView();
            new ChooserDialog().with(TeacherActivity.this)
                    .withStartFile(Environment.getExternalStorageState())
                    .withChosenListener((path, pathFile) -> {
                        RequestBody requestFile = RequestBody.create(MediaType.parse(FileUtil.getMimeType(path)), pathFile);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("File", pathFile.getName(), requestFile);
                        showLoading();
                        if (FileUtil.getMimeType(path).equals("application/pdf")) {
                            mPresenter.getFilePDF(body);
                        } else if (FileUtil.getMimeType(path).equals("application/ppt")
                                || FileUtil.getMimeType(path).equals("application/vnd.ms-powerpoint")) {
                            mPresenter.getFilePPT(body);
                        } else if (FileUtil.getMimeType(path).equals("application/pptx")
                                || FileUtil.getMimeType(path).equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
                            mPresenter.getFilePPTX(body);
                        } else {
                            showErrorDialog("Thông báo", "Định dạng không thể convert được", liveDialog -> liveDialog.dismiss());
                        }

                    })
                    .build()
                    .show();

        });
        ibAddPage.setOnClickListener(view -> {
            mPenSurfaceView.setPageEffectListener(() -> ibAddPage.setClickable(true));
            mPenSurfaceView.closeControl();
            closeSettingView();
            // Create a page next to the current page.
            mPenPageDoc = mPenNoteDoc.insertPage(mPenNoteDoc.getPageIndexById(mPenPageDoc.getId()) + 1);
            mPresenter.getBackgroundColor();
            mPenPageDoc.clearHistory();
            view.setClickable(false);
            mPenSurfaceView.setPageDoc(mPenPageDoc, SpenSurfaceView.PAGE_TRANSITION_EFFECT_RIGHT, SpenSurfaceView.PAGE_TRANSITION_EFFECT_TYPE_SHADOW, 0);
            tvNumberPage.setText(String.format(getString(R.string.tv_teacher_page_number), mPenNoteDoc.getPageIndexById(mPenPageDoc.getId())));
        });
        ibUndo.setOnClickListener(undoRedoOnClickListener);
        ibUndo.setEnabled(mPenPageDoc.isUndoable());
        ibRedo.setOnClickListener(undoRedoOnClickListener);
        ibRedo.setEnabled(mPenPageDoc.isRedoable());
        ibRecord.setOnClickListener(view -> {
            if (recordStatus == IS_RECORDING) {
                if (System.currentTimeMillis() - onTimeRecord < MIN_TIME_RECORD) {
                    showCautionDialog(getResources().getString(R.string.teacher_min_time_record_error), "", liveDialog -> {
                        liveDialog.dismiss();
                    });
                } else {
                    recordStatus = IS_CLOSED_RECORD;
                    closeSettingView();
//                    Toast.makeText(TeacherActivity.this, "Video is saved", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "Stopping Recording");
                    ToastUtil.show(this, getString(R.string.teacher_save_pause));
                    stopScreenSharing();
                    stopCountUpTimer();
                }
            } else {
                if (listRecordsName.size() >= 1 && listRecordsPath.size() >= 1) {
                    checkSessionRecord = true;
                    recordStatus = IS_RECORDING;
                    mPresenter.onResumeRecord(RecordUtil.baseDir + "/" + listRecordsName.get(listRecordsName.size() - 1) + listRecordsName.size() + ".mp4", listRecordsName.get(listRecordsName.size() - 1) + listRecordsName.size());
                    ToastUtil.show(this, getString(R.string.teacher_save_resume));
                } else {
                    SettingVideoDFragment dialogFragment = SettingVideoDFragment.newInstance();
                    dialogFragment.show(getSupportFragmentManager(), dialogFragment.getClass().getSimpleName());
                }
            }
        });
        ibSave.setOnClickListener(view -> {
            if (System.currentTimeMillis() - onTimeRecord < MIN_TIME_RECORD) {
                showCautionDialog(getResources().getString(R.string.teacher_min_time_record_error), "", liveDialog -> {
                    liveDialog.dismiss();
                });
            } else {
                showConfirmSaveDialog();
            }
        });
        selectButton(ibBrush);
        imvSetting.setOnClickListener(view -> {
            startActivity(SettingActivity.class);
        });
        mfaTopDown.setOnClickListener(view -> {
            if (llMenuMore.getVisibility() == View.VISIBLE) {
                llMenuMore.setVisibility(View.GONE);
//                tvNumberPage.setVisibility(View.GONE);
            } else {
                llMenuMore.setVisibility(View.VISIBLE);
//                tvNumberPage.setVisibility(View.VISIBLE);
            }
        });
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mfaLeftRight.setOnClickListener(view -> {
                if (rlCamera.getVisibility() == View.VISIBLE) {
                    rlCamera.setVisibility(View.GONE);
                    isShowCamera = false;
                } else {
                    rlCamera.setVisibility(View.VISIBLE);
                    isShowCamera = true;
                }
            });
        } else {
        }
    }

    private void showConfirmSaveDialog() {
        if (checkSessionRecord) {
            DialogUtil.showConfirmDialog(
                    this,
                    R.string.app_name,
                    R.string.teacher_save_question,
                    R.mipmap.ic_launcher,
                    R.string.teacher_save_negative,
                    R.string.teacher_save_positive,
                    (dialogInterface, postion) -> {
                        normalSave();
                        EventBus.getDefault().postSticky(new RecordSuccessEvent());
                        finish();
                    },
                    (dialogInterface, postion) -> {
                        normalSave();
                        EventBus.getDefault().postSticky(new RecordSuccessEvent());
                        startActivity(new Intent(this, TeacherActivity.class));
                        finish();
                    }
            );
        }
    }

    private void normalSave() {
        closeSettingView();
//                    Toast.makeText(TeacherActivity.this, "Video is saved", Toast.LENGTH_SHORT).show();
        Log.v(TAG, "Stopping Recording");
        stopScreenSharing();
        if (checkSessionRecord == true && listRecordsPath.size() >= 2) {
            RecordUtil.getInstance().appendVideo(listRecordsPath, listRecordsName);
        }
        clearRecord();
    }

    private void clearRecord() {
        recordStatus = 0;
        checkSessionRecord = false;
        listRecordsName.clear();
        listRecordsPath.clear();
        showListVideo();
    }

    private void showListVideo() {
        ToastUtil.show(this, getString(R.string.teacher_save_success));
    }

    @Override
    public void onDoneSetTime(String time) {
        isShowSetTime = true;
        mPenSurfaceView.setTouchListener(touchListenerTemporaryBrush());
    }

    @Override
    public void onDone(String pathVideo, int bitRate, int frameRate, String originName) {
        checkSessionRecord = true;
        recordStatus = IS_RECORDING;
        mPresenter.onDoneSettingVideo(pathVideo, bitRate, frameRate, originName);
    }

    @Override
    public void onConnectionSuccessRtmp() {
        runOnUiThread(() -> {
            hideLoading();
            Toast.makeText(TeacherActivity.this, "Kết nối thành công", Toast.LENGTH_SHORT).show();
            try {
                mPresenter.startRecord(rtmpDisplay);
                ibRecord.setImageResource(R.drawable.ic_stop);
            } catch (Exception e) {
                rtmpDisplay.stopRecord();
                Toast.makeText(this, getResources().getString(R.string.error_try_later), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnectionFailedRtmp(@NonNull String reason) {
        runOnUiThread(() -> {
            hideLoading();
            Toast.makeText(this, getResources().getString(R.string.error_try_later), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDisconnectRtmp() {
        Toast.makeText(this, getResources().getString(R.string.error_disconnect), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthErrorRtmp() {

    }

    @Override
    public void onAuthSuccessRtmp() {

    }

    private void selectButton(ImageView ivSelected) {
        ibBrush.setSelected(false);
        ibTempBrush.setSelected(false);
        ibEraser.setSelected(false);
        ibSelection.setSelected(false);
        ibAddText.setSelected(false);
        ibSelection.setSelected(false);
        ibRecognizeShape.setSelected(false);
        ivSelected.setSelected(true);
        closeSettingView();
    }

    private void closeSettingView() {
        // Close all the setting views.
        mEraserSettingView.setVisibility(SpenSurfaceView.GONE);
        mPenSettingView.setVisibility(SpenSurfaceView.GONE);
        mTextSettingView.setVisibility(SpenSurfaceView.GONE);
    }


    @Override
    public void setBackgroundColor(int backgroundColor) {
        setBackgroundSdk(backgroundColor);
    }

    @Override
    public void executeStreamVideo(int resultCode, Intent data) {
        mPresenter.executeStreamVideo(TeacherActivity.this, rtmpDisplay, resultCode, data);
    }

    @Override
    public void executeRecordVideo(int resultCode, Intent data) {
        mPresenter.executeRecordVideo(TeacherActivity.this, rtmpDisplay, resultCode, data);
    }

    @Override
    public void startRtmpDisplay(boolean isLogin, String originName) {
        listRecordsName.add(originName);
        listRecordsPath.add(RecordUtil.baseDir + "/" + originName + ".mp4");
        showLoading();
        if (isLogin) {
            startActivityForResult(rtmpDisplay.sendIntent(), REQUEST_CODE_STREAM);
        } else {
            startActivityForResult(rtmpDisplay.sendIntent(), REQUEST_CODE_RECORD);
        }
    }

    @Override
    public void setImageRecordStop() {
        ibRecord.setImageResource(R.drawable.ic_record);
        ibRecord.setEnabled(true);
    }

    @Override
    public void endYoutubeEventTask(String broadcastID) {
        mPresenter.endYoutubeEventTask(TeacherActivity.this, broadcastID);
    }

    @Override
    public void showFileConvert(FileResponse response) {
        SlowAsyncTask slowAsyncTask = new SlowAsyncTask();
        slowAsyncTask.execute(response);
    }

    @Override
    public void setZoom(int settingZoomCheckedItem) {
        if (settingZoomCheckedItem == IS_ZOOM) {
            mPenSurfaceView.setToolTypeAction(SpenSimpleSurfaceView.TOOL_FINGER, SpenSimpleSurfaceView.ACTION_GESTURE);
        } else if (settingZoomCheckedItem == IS_NON_ZOOM) {
            mPenSurfaceView.setToolTypeAction(SpenSimpleSurfaceView.TOOL_FINGER, SpenSimpleSurfaceView.ACTION_NONE);
        }
    }

    @Override
    public void preExecuteVideo() {
        isSaveRecord = false;
        onTimeRecord = System.currentTimeMillis();
        showLoading();
    }

    @Override
    public void postExecuteStreamVideo() {
        if (listRecordsName.size() > 1 && listRecordsPath.size() > 1) {
            ToastUtil.show(TeacherActivity.this, getString(R.string.teacher_save_resume));
        } else {
            ToastUtil.show(TeacherActivity.this, getString(R.string.teacher_record_start));
        }
        startCountUpTimer();
    }

    @Override
    public void postExecuteRecordVideo() {
        ibRecord.setImageResource(R.drawable.ic_stop);
        if (listRecordsName.size() > 1 && listRecordsPath.size() > 1) {
            ToastUtil.show(TeacherActivity.this, getString(R.string.teacher_save_resume));
        } else {
            ToastUtil.show(TeacherActivity.this, getString(R.string.teacher_record_start));
        }
        startCountUpTimer();
    }

    private void callGalleryForInputImage(int requestCode) {
        // Get an image from the gallery.
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png"};
            galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(galleryIntent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            // Process image request for the background.
            if (requestCode == REQUEST_CODE_SELECT_IMAGE_BACKGROUND) {
                // Get the image's URI and use the location for background image.
                Uri imageFileUri = data.getData();
                String imageRealPath = getRealPathFromURI(imageFileUri);
                mPenPageDoc.setBackgroundImage(imageRealPath);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mPenPageDoc.setBackgroundImageMode(SpenPageDoc.BACKGROUND_IMAGE_MODE_FIT);
                } else {
                    mPenPageDoc.setBackgroundImageMode(SpenPageDoc.BACKGROUND_IMAGE_MODE_STRETCH);
                }
                mPenSurfaceView.update();
            }
            if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
                Uri imageFileUri = data.getData();
                String imageRealPath = getRealPathFromURI(imageFileUri);
                SpenObjectImage imgObj = new SpenObjectImage();
                imgObj.setImage(imageRealPath);
                RectF rect1 = new RectF(0, 0, penViewContainer.getWidth(), penViewContainer.getHeight());
                imgObj.setRect(rect1, true);
                mPenPageDoc.appendObject(imgObj);
                mPenSurfaceView.update();
            }

            if (requestCode == REQUEST_CODE_STREAM) {
                hideLoading();
                mPresenter.initStream(resultCode, data);
            }
            if (requestCode == REQUEST_CODE_RECORD) {
                hideLoading();
                executeRecordVideo(resultCode, data);
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        return DeviceUtil.getRealPathFromURI(this, contentURI);
    }

    private void stopScreenSharing() {
        Log.i(TAG, "MediaProjection Stopped");
        isSaveRecord = true;
        mPresenter.stopScreenSharing(rtmpDisplay);
    }

    private void startCountUpTimer() {
        if (!runningChronometer) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            runningChronometer = true;
        }
    }

    private void stopCountUpTimer() {
        if (runningChronometer) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            runningChronometer = false;
        }
    }

    private View.OnClickListener undoRedoOnClickListener = view -> {
        if (mPenPageDoc == null) {
            return;
        }
        // Undo button is clicked.
        if (view.equals(ibUndo)) {
            if (mPenPageDoc.isUndoable()) {
                SpenPageDoc.HistoryUpdateInfo[] userData = mPenPageDoc.undo();
                mPenSurfaceView.updateUndo(userData);
            }
            // Redo button is clicked.
        } else if (view.equals(ibRedo)) {
            if (mPenPageDoc.isRedoable()) {
                SpenPageDoc.HistoryUpdateInfo[] userData = mPenPageDoc.redo();
                mPenSurfaceView.updateRedo(userData);
            }
        }
    };

    private class SlowAsyncTask extends AsyncTask<FileResponse, Void, Void> {

        @Override
        protected Void doInBackground(FileResponse... fileResponses) {
            for (int i = 0; i < fileResponses[0].getFiles().size(); i++) {
                File file = FileUtil.saveImage(fileResponses[0].getFiles().get(i).getFileName(), fileResponses[0].getFiles().get(i).getFileData());
                if (isShowCamera) {
                    SpenObjectImage imgObj = new SpenObjectImage();
                    imgObj.setImage(file.getAbsolutePath());
                    RectF rect1 = new RectF(0, 0, penViewContainer.getWidth(), penViewContainer.getHeight());
                    imgObj.setRect(rect1, true);
                    mPenPageDoc.appendObject(imgObj);
                } else {
                    mPenPageDoc.setBackgroundImage(file.getAbsolutePath());
                    mPenPageDoc.setBackgroundImageMode(SpenPageDoc.BACKGROUND_IMAGE_MODE_FIT);
                }
                mPenPageDoc = mPenNoteDoc.insertPage(mPenNoteDoc.getPageIndexById(mPenPageDoc.getId()) + 1);
                mPresenter.getBackgroundColor();
                mPenPageDoc.clearHistory();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
//            super.onPreExecute();
            showLoading();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
            hideLoading();
            mPenPageDoc = mPenNoteDoc.getPage(0);
            mPenSurfaceView.setPageDoc(mPenPageDoc, true);
            mPenSurfaceView.update();
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - onTimeRecord < MIN_TIME_RECORD) {
            showCautionDialog(getResources().getString(R.string.teacher_min_time_record_error), "", liveDialog -> {
                liveDialog.dismiss();
            });
        } else {
            if (isSaveRecord) {
                super.onBackPressed();
            } else {
                showCautionDialog(getResources().getString(R.string.teacher_no_save_error), "", liveDialog -> {
                    liveDialog.dismiss();
                });
            }
        }
    }
}
