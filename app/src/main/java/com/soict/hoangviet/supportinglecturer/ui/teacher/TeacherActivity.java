package com.soict.hoangviet.supportinglecturer.ui.teacher;

import android.os.Handler;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.samsung.android.sdk.pen.engine.SpenSurfaceView;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.customview.AutoFitTextureView;
import com.soict.hoangviet.supportinglecturer.customview.MovableFloatingActionButton;
import com.soict.hoangviet.supportinglecturer.customview.settime.SettingTimeTempBushDFragment;
import com.soict.hoangviet.supportinglecturer.customview.settingvideo.SettingVideoDFragment;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseSamsungSpenSdkActivity;

import net.ossrs.rtmp.ConnectCheckerRtmp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeacherActivity extends BaseSamsungSpenSdkActivity implements TeacherView, SettingVideoDFragment.OnClickSettingVideo, ConnectCheckerRtmp, SettingTimeTempBushDFragment.OnClickSettingTime, View.OnTouchListener {
    @Inject
    TeacherPresenter<TeacherView> mPresenter;
    private static final String TAG = TeacherActivity.class.getSimpleName();
    private final int MODE_PEN = 0;
    private final int MODE_IMG_OBJ = 1;
    private final int MODE_TEXT_OBJ = 2;
    @BindView(R.id.spenViewLayout)
    RelativeLayout spenViewLayout;
    @BindView(R.id.spenViewContainer)
    FrameLayout spenViewContainer;
    @BindView(R.id.ivPen)
    ImageButton ivPen;
    @BindView(R.id.ibTempBrush)
    ImageButton ibTempBrush;
    @BindView(R.id.ivEraser)
    ImageButton ivEraser;
    @BindView(R.id.ivAddImage)
    ImageButton ivAddImage;
    @BindView(R.id.ibInsertImage)
    ImageButton ibInsertImage;
    @BindView(R.id.ibInsertFile)
    ImageButton ibInsertFile;
    @BindView(R.id.ibAddVideo)
    ImageButton ibAddVideo;
    @BindView(R.id.ibCaptureScreen)
    ImageButton ibCaptureScreen;
    @BindView(R.id.ibText)
    ImageButton ibText;
    @BindView(R.id.ibSelection)
    ImageButton ibSelection;
    @BindView(R.id.ibBound)
    ImageButton ibBound;
    @BindView(R.id.ibAddPage)
    ImageButton ibAddPage;
    @BindView(R.id.ivUndo)
    ImageButton ivUndo;
    @BindView(R.id.ivRedo)
    ImageButton ivRedo;
    @BindView(R.id.ibRecord)
    ImageButton ibRecord;
    @BindView(R.id.ibSave)
    ImageButton ibSave;
    @BindView(R.id.llMenuMore)
    LinearLayout llMenuMore;
    @BindView(R.id.simpleChronometer)
    Chronometer simpleChronometer;
    @BindView(R.id.mfa_top_down)
    MovableFloatingActionButton mfaTopDown;
    @BindView(R.id.drawView)
    RelativeLayout drawView;
    private int mMode = MODE_PEN;
    private int mToolType = SpenSurfaceView.TOOL_SPEN;
    private final int CONTEXT_MENU_RUN_ID = 0;
    private long onTimeRecord = -1;
    private static final int DISPLAY_WIDTH = 1920;
    private static final int DISPLAY_HEIGHT = 1080;
    private static final int REQUEST_CODE_SELECT_IMAGE_BACKGROUND = 99;
    private static final int REQUEST_CODE_SELECT_IMAGE = 98;
    private static final int REQUEST_CODE_RECORD = 94;
    private static final int REQUEST_CODE_STREAM = 95;
    static final int REQUEST_CAMERA_PERMISSION = 1009;
    static final int PICK_PDF_CODE = 1010;
    //Check state orientation of output image
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final long MIN_TIME_RECORD = 6000L;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }


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
        super.initView();
        onAttachPresenter();
    }

    private void onAttachPresenter() {
        mPresenter.onAttach(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onDoneSetTime(String time) {
        isShowSetTime = true;
        mPenSurfaceView.setTouchListener(touchListenerTemporaryBrush());
    }

    @Override
    public void onDone(String pathVideo, int bitRate, int frameRate, String originName) {

    }

    @Override
    public void onConnectionSuccessRtmp() {

    }

    @Override
    public void onConnectionFailedRtmp(@NonNull String reason) {

    }

    @Override
    public void onNewBitrateRtmp(long bitrate) {

    }

    @Override
    public void onDisconnectRtmp() {

    }

    @Override
    public void onAuthErrorRtmp() {

    }

    @Override
    public void onAuthSuccessRtmp() {

    }



}
