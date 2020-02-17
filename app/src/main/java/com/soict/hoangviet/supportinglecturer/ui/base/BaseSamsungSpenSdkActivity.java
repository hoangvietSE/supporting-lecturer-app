package com.soict.hoangviet.supportinglecturer.ui.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pen.Spen;
import com.samsung.android.sdk.pen.SpenSettingEraserInfo;
import com.samsung.android.sdk.pen.SpenSettingPenInfo;
import com.samsung.android.sdk.pen.SpenSettingTextInfo;
import com.samsung.android.sdk.pen.document.SpenNoteDoc;
import com.samsung.android.sdk.pen.document.SpenObjectBase;
import com.samsung.android.sdk.pen.document.SpenObjectContainer;
import com.samsung.android.sdk.pen.document.SpenObjectImage;
import com.samsung.android.sdk.pen.document.SpenObjectStroke;
import com.samsung.android.sdk.pen.document.SpenObjectTextBox;
import com.samsung.android.sdk.pen.document.SpenPageDoc;
import com.samsung.android.sdk.pen.engine.SpenContextMenuItemInfo;
import com.samsung.android.sdk.pen.engine.SpenControlBase;
import com.samsung.android.sdk.pen.engine.SpenControlListener;
import com.samsung.android.sdk.pen.engine.SpenFlickListener;
import com.samsung.android.sdk.pen.engine.SpenObjectRuntime;
import com.samsung.android.sdk.pen.engine.SpenObjectRuntimeInfo;
import com.samsung.android.sdk.pen.engine.SpenObjectRuntimeManager;
import com.samsung.android.sdk.pen.engine.SpenSurfaceView;
import com.samsung.android.sdk.pen.engine.SpenTextChangeListener;
import com.samsung.android.sdk.pen.engine.SpenTouchListener;
import com.samsung.android.sdk.pen.plugin.interfaces.SpenObjectRuntimeInterface;
import com.samsung.android.sdk.pen.settingui.SpenSettingEraserLayout;
import com.samsung.android.sdk.pen.settingui.SpenSettingPenLayout;
import com.samsung.android.sdk.pen.settingui.SpenSettingTextLayout;
import com.soict.hoangviet.supportinglecturer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public abstract class BaseSamsungSpenSdkActivity extends BaseCameraActivity {
    @BindView(R.id.spenViewLayout)
    protected RelativeLayout penViewLayout;
    @BindView(R.id.spenViewContainer)
    protected FrameLayout penViewContainer;
    @BindView(R.id.tvPageNumber)
    protected TextView tvNumberPage;
    @BindView(R.id.ivUndo)
    protected ImageButton ibUndo;
    @BindView(R.id.ivRedo)
    protected ImageButton ibRedo;
    private final int CONTEXT_MENU_RUN_ID = 0;
    protected SpenNoteDoc mPenNoteDoc;
    protected SpenPageDoc mPenPageDoc;
    protected SpenSurfaceView mPenSurfaceView;
    protected SpenSettingPenLayout mPenSettingView;
    protected SpenSettingEraserLayout mEraserSettingView;
    protected SpenSettingTextLayout mTextSettingView;
    private SpenObjectRuntimeManager mSpenObjectRuntimeManager;
    private List<SpenObjectRuntimeInfo> mSpenObjectRuntimeInfoList;
    private SpenObjectRuntime mVideoRuntime;
    private Integer backgorundColor;
    protected boolean isShowSetTime = true;
    private Handler mStrokeHandler;
    private int timeTempBush = 2;
    protected final int MODE_PEN = 0;
    protected final int MODE_IMG_OBJ = 1;
    protected final int MODE_TEXT_OBJ = 2;
    protected int mMode = MODE_PEN;
    private int mToolType = SpenSurfaceView.TOOL_SPEN;
    protected SpenObjectRuntimeInfo mObjectRuntimeInfo;

    @Override
    protected void initView() {
        super.initView();
        initSamSungPen();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPenSurfaceView.setSetPageDocListener(spenPageDoc -> {
            mPenSurfaceView.setPageDoc(spenPageDoc, true);
            spenPageDoc.setHistoryListener(mHistoryListener);
            spenPageDoc.setAutoUnloadable(true);
        });
    }

    private void initSamSungPen() {
        // Initialize Pen.
        boolean isSpenFeatureEnabled = false;
        Spen spenPackage = new Spen();
        try {
            spenPackage.initialize(this);
            isSpenFeatureEnabled = spenPackage.isFeatureEnabled(Spen.DEVICE_PEN);
        } catch (SsdkUnsupportedException e) {
            e.printStackTrace();
            finish();
        } catch (Exception ex) {
            finish();
        }
        // Create PenSettingView
        mPenSettingView = new SpenSettingPenLayout(this, "", penViewLayout);
        if (mPenSettingView == null) {
            finish();
        }
        penViewContainer.addView(mPenSettingView);
        // Create EraserSettingView
        //noinspection deprecation
        mEraserSettingView = new SpenSettingEraserLayout(this, "", penViewLayout);
        if (mEraserSettingView == null) {
            finish();
        }
        penViewContainer.addView(mEraserSettingView);
        // Create TextSettingView.
        mTextSettingView = new SpenSettingTextLayout(this, "", new HashMap<>(), penViewLayout);
        if (mTextSettingView == null) {
            finish();
        }
        penViewContainer.addView(mTextSettingView);
        // Create SurfacePenView
        mPenSurfaceView = new SpenSurfaceView(this);
        if (mPenSurfaceView == null) {
            finish();
        }
        penViewLayout.addView(mPenSurfaceView);
        mPenSettingView.setCanvasView(mPenSurfaceView);
        mEraserSettingView.setCanvasView(mPenSurfaceView);
        // Get the dimensions of the screen.
        Display display = getWindowManager().getDefaultDisplay();
        Rect rect = new Rect();
        display.getRectSize(rect);
        // Create SpenNoteDoc.
        try {
            mPenNoteDoc = new SpenNoteDoc(this, rect.width(), rect.height());
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

        // After adding a page to NoteDoc, get an instance and set it as a member variable.
        mPenPageDoc = mPenNoteDoc.appendPage();
        setBackgroundSdk(backgorundColor);
        mPenPageDoc.clearHistory();

        // Set PageDoc to View.
        mPenSurfaceView.setPageDoc(mPenPageDoc, true);
        tvNumberPage.setText(String.format(getString(R.string.tv_teacher_page_number), mPenNoteDoc.getPageIndexById(mPenPageDoc.getId())));
        initPenSettingInfo();
        // Register the listeners.
        mPenSurfaceView.setColorPickerListener((color, x, y) -> {
            if (mPenSettingView != null) {
                SpenSettingPenInfo penInfo = mPenSettingView.getInfo();
                penInfo.color = color;
                mPenSettingView.setInfo(penInfo);
            }
        });
        mPenSurfaceView.setTextChangeListener(textChangeListener());
        mPenSurfaceView.setFlickListener(flickListener());
        mPenSurfaceView.setControlListener(mControlListener);
        mPenSurfaceView.setPreTouchListener(onPreTouchSurfaceViewListener);
        //noinspection deprecation
        mEraserSettingView.setEraserListener(() -> {
            // Handle the Clear All button in EraserSettingView.
            mPenPageDoc.removeAllObject();
            mPenSurfaceView.update();
        });

        mPenPageDoc.setHistoryListener(mHistoryListener);
        // Set up the ObjectRuntimeManager.
        mSpenObjectRuntimeManager = new SpenObjectRuntimeManager(this);
        mSpenObjectRuntimeInfoList = new ArrayList<>();
        mSpenObjectRuntimeInfoList = mSpenObjectRuntimeManager.getObjectRuntimeInfoList();

        if (!isSpenFeatureEnabled) {
            mPenSurfaceView.setToolTypeAction(SpenSurfaceView.TOOL_FINGER, SpenSurfaceView.ACTION_STROKE);
//                    Toast.makeText(TeacherActivity.this, "Device does not support Spen. \n You can draw stroke by finger.", Toast.LENGTH_SHORT).show();
        }
    }

    private SpenPageDoc.HistoryListener mHistoryListener = new SpenPageDoc.HistoryListener() {
        @Override
        public void onCommit(SpenPageDoc page) {
        }

        @Override
        public void onUndoable(SpenPageDoc page, boolean undoable) {
            // Enable or disable Undo button depending on its availability.
            ibUndo.setEnabled(undoable);
        }

        @Override
        public void onRedoable(SpenPageDoc page, boolean redoable) {
            // Enable or disable Redo button depending on its availability.
            ibRedo.setEnabled(redoable);
        }
    };

    private void initPenSettingInfo() {
        // Initialize pen settings.
        SpenSettingPenInfo penInfo = new SpenSettingPenInfo();
        penInfo.color = Color.BLUE;
        penInfo.size = 10;
        mPenSurfaceView.setPenSettingInfo(penInfo);
        mPenSettingView.setInfo(penInfo);

        SpenSettingTextInfo textInfo = mTextSettingView.getInfo();
        textInfo.color = Color.BLACK;
        mTextSettingView.setInfo(textInfo);

        // Initialize eraser settings.
        SpenSettingEraserInfo eraserInfo = new SpenSettingEraserInfo();
        eraserInfo.size = 30;
        mPenSurfaceView.setEraserSettingInfo(eraserInfo);
        mEraserSettingView.setInfo(eraserInfo);
    }

    @NonNull
    private SpenTextChangeListener textChangeListener() {
        return new SpenTextChangeListener() {
            @Override
            public void onChanged(SpenSettingTextInfo spenSettingTextInfo, int state) {
                if (mTextSettingView != null) {
                    if (state == CONTROL_STATE_SELECTED) {
                        mTextSettingView.setInfo(spenSettingTextInfo);
                    }
                }
            }

            @Override
            public boolean onSelectionChanged(int i, int i1) {
                return false;
            }

            @Override
            public void onMoreButtonDown(SpenObjectTextBox spenObjectTextBox) {

            }

            @Override
            public void onFocusChanged(boolean b) {

            }
        };
    }

    @NonNull
    private SpenFlickListener flickListener() {
        return direction -> {
            int pageIndex = mPenNoteDoc.getPageIndexById(mPenPageDoc.getId());
            int pageCount = mPenNoteDoc.getPageCount();
            boolean checkSetPageDoc = false;
            if (pageCount > 1) {
                // Flick left and turn to the previous page.
                if (direction == SpenFlickListener.DIRECTION_LEFT) {
                    mPenPageDoc = mPenNoteDoc.getPage((pageIndex + pageCount - 1) % pageCount);
                    if (mPenSurfaceView.setPageDoc(mPenPageDoc, SpenSurfaceView.PAGE_TRANSITION_EFFECT_LEFT, SpenSurfaceView.PAGE_TRANSITION_EFFECT_TYPE_SHADOW, 0)) {
                        checkSetPageDoc = true;
                    } else {
                        checkSetPageDoc = false;
                        mPenPageDoc = mPenNoteDoc.getPage(pageIndex);
                    }
                    // Flick right and turn to the next page.
                } else if (direction == SpenFlickListener.DIRECTION_RIGHT) {
                    mPenPageDoc = mPenNoteDoc.getPage((pageIndex + 1) % pageCount);
                    if (mPenSurfaceView.setPageDoc(mPenPageDoc, SpenSurfaceView.PAGE_TRANSITION_EFFECT_RIGHT, SpenSurfaceView.PAGE_TRANSITION_EFFECT_TYPE_SHADOW, 0)) {
                        checkSetPageDoc = true;
                    } else {
                        checkSetPageDoc = false;
                        mPenPageDoc = mPenNoteDoc.getPage(pageIndex);
                    }
                }
                if (checkSetPageDoc) {
                    tvNumberPage.setText(String.format(getString(R.string.tv_teacher_page_number), mPenNoteDoc.getPageIndexById(mPenPageDoc.getId())));
                }
                return true;
            }
            return false;
        };
    }

    SpenControlListener mControlListener = new SpenControlListener() {
        @Override
        public boolean onCreated(ArrayList<SpenObjectBase> objectList, ArrayList<Rect> relativeRectList, ArrayList<SpenContextMenuItemInfo> menu, ArrayList<Integer> styleList, int pressType, PointF point) {
            if (objectList == null) {
                return false;
            }
            // Display the context menu if any SOR information is found.
            if (objectList.get(0).getSorInfo() != null) {
                menu.add(new SpenContextMenuItemInfo(CONTEXT_MENU_RUN_ID, "Run", true));
                return true;
            }
            return true;
        }

        @Override
        public boolean onMenuSelected(
                ArrayList<SpenObjectBase> objectList, int itemId) {
            if (objectList == null) {
                return true;
            }
            if (itemId == CONTEXT_MENU_RUN_ID) {
                SpenObjectBase object = objectList.get(0);
                mPenSurfaceView.getControl().setContextMenuVisible(false);
                mPenSurfaceView.getControl().setStyle(SpenControlBase.STYLE_BORDER_STATIC);
                // Set up listener and make it play.
                mVideoRuntime.setListener(objectRuntimeListener);
                mVideoRuntime.start(object, getRealRect(object.getRect()),
                        mPenSurfaceView.getPan(), mPenSurfaceView.getZoomRatio(),
                        mPenSurfaceView.getFrameStartPosition(), penViewLayout);
                mPenSurfaceView.update();
            }
            return false;
        }

        @Override
        public void onObjectChanged(ArrayList<SpenObjectBase> object) {
        }

        @Override
        public void onRectChanged(RectF rect, SpenObjectBase object) {
        }

        @Override
        public void onRotationChanged(float angle, SpenObjectBase objectBase) {
        }

        @Override
        public boolean onClosed(ArrayList<SpenObjectBase> objectList) {
            if (mVideoRuntime != null)
                mVideoRuntime.stop(true);
            return false;
        }
    };

    SpenObjectRuntime.UpdateListener objectRuntimeListener = new SpenObjectRuntime.UpdateListener() {

        @Override
        public void onCompleted(Object objectBase) {
            if (mPenSurfaceView != null) {
                SpenControlBase control = mPenSurfaceView.getControl();
                if (control != null) {
                    control.setContextMenuVisible(true);
                    //noinspection deprecation
                    mPenSurfaceView.updateScreenFrameBuffer();
                    mPenSurfaceView.update();
                }
            }
//            ibAddCamera.setClickable(true);
        }

        @Override
        public void onObjectUpdated(RectF rect, Object objectBase) {
            if (mPenSurfaceView != null) {
                SpenControlBase control = mPenSurfaceView.getControl();
                if (control != null) {
                    control.fit();
                    control.invalidate();
                    mPenSurfaceView.update();
                }
            }
        }

        @Override
        public void onCanceled(int state, Object objectBase) {
            if (state == SpenObjectRuntimeInterface.CANCEL_STATE_INSERT) {
                mPenPageDoc.removeObject((SpenObjectBase) objectBase);
                mPenPageDoc.removeSelectedObject();
                mPenSurfaceView.closeControl();
                mPenSurfaceView.update();
            } else if (state == SpenObjectRuntimeInterface.CANCEL_STATE_RUN) {
                mPenSurfaceView.closeControl();
                mPenSurfaceView.update();
            }
//            ibAddCamera.setClickable(true);
        }
    };

    private RectF getRealRect(RectF rect) {
        float panX = mPenSurfaceView.getPan().x;
        float panY = mPenSurfaceView.getPan().y;
        float zoom = mPenSurfaceView.getZoomRatio();
        PointF startPoint = mPenSurfaceView.getFrameStartPosition();
        RectF realRect = new RectF();
        realRect.set(
                (rect.left - panX) * zoom + startPoint.x,
                (rect.top - panY) * zoom + startPoint.y,
                (rect.right - panX) * zoom + startPoint.x,
                (rect.bottom - panY) * zoom + startPoint.y
        );
        return realRect;
    }

    protected RectF getRealPoint(float x, float y, float width, float height) {
        float panX = mPenSurfaceView.getPan().x;
        float panY = mPenSurfaceView.getPan().y;
        float zoom = mPenSurfaceView.getZoomRatio();
        width *= zoom;
        height *= zoom;
        RectF realRect = new RectF();
        realRect.set(
                (x - width / 2) / zoom + panX, (y - height / 2) / zoom + panY,
                (x + width / 2) / zoom + panX, (y + height / 2) / zoom + panY);
        return realRect;
    }

    private SpenTouchListener onPreTouchSurfaceViewListener = (view, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
//                enableButton(false);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                enableButton(true);
                break;
        }
        return false;
    };

    protected void setBackgroundSdk(Integer backgroundColor) {
        if (backgroundColor != null) {
            mPenPageDoc.setBackgroundColor(backgroundColor);
        } else {
            mPenPageDoc.setBackgroundColor(0xFFD6E6F5);
        }
        mPenSurfaceView.update();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //   prevent memory leaks when you application closes.
        if (mPenSettingView != null) {
            mPenSettingView.close();
        }
        if (mStrokeHandler != null) {
            mStrokeHandler.removeCallbacks(mStrokeRunnable);
            mStrokeHandler = null;
        }
        if (mEraserSettingView != null) {
            mEraserSettingView.close();
        }
        if (mSpenObjectRuntimeManager != null) {
            if (mVideoRuntime != null) {
                mVideoRuntime.stop(true);
                mSpenObjectRuntimeManager.unload(mVideoRuntime);
            }
            mSpenObjectRuntimeManager.close();
        }
        if (mPenPageDoc.isRecording()) {
            mPenPageDoc.stopRecord();
        }
        if (mPenSurfaceView.getReplayState() == SpenSurfaceView.REPLAY_STATE_PLAYING) {
            mPenSurfaceView.stopReplay();
        }
        if (mTextSettingView != null) {
            mTextSettingView.close();
        }
        //  Close the text control
        mPenSurfaceView.closeControl();
        if (mPenSurfaceView != null) {
            mPenSurfaceView.close();
            mPenSurfaceView = null;
        }
        if (mPenNoteDoc != null) {
            try {
                mPenNoteDoc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mPenNoteDoc = null;
        }
    }

    @NonNull
    protected SpenTouchListener touchListenerTemporaryBrush() {
        return (v, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // When ACTION_DOWN occurs before mStrokeRunnable is set in a queue, the mStrokeRunnable that waits is removed.
                    if (mStrokeHandler != null) {
                        mStrokeHandler.removeCallbacks(mStrokeRunnable);
                        mStrokeHandler = null;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // Generate Handler to put mStrokeRunnable in a queue when it takes 1000 milliseconds after ACTION_UP occurred.
                    mStrokeHandler = new Handler();
                    mStrokeHandler.postDelayed(mStrokeRunnable, timeTempBush * 1000);
                    break;
            }
            return true;
        };
    }

    private final Runnable mStrokeRunnable = new Runnable() {
        @Override
        public void run() {
            // Get TemporaryStroke to resize the object by 1/2.
            mPenSurfaceView.stopTemporaryStroke();
            mPenSurfaceView.startTemporaryStroke();
            mPenSurfaceView.update();
        }
    };

    @NonNull
    protected SpenTouchListener touchListenerBrush() {
        return (view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && event.getToolType(0) == mToolType) {
                // Checks whether the control is generated or not.
                SpenControlBase control = mPenSurfaceView.getControl();
                if (control == null) {
                    // When touching the screen in Insert ObjectImage mode.
                    if (mMode == MODE_IMG_OBJ) {
//                     Set the Bitmap file to ObjectImage.
                        SpenObjectImage imgObj = new SpenObjectImage();
                        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_shape);
                        imgObj.setImage(imageBitmap);
//                     Specify the location where ObjectImage is inserted and add PageDoc.
                        float imgWidth = imageBitmap.getWidth();
                        float imgHeight = imageBitmap.getHeight();
                        RectF rect1 = getRealPoint(event.getX(), event.getY(), imgWidth, imgHeight);
                        imgObj.setRect(rect1, true);
                        mPenPageDoc.appendObject(imgObj);
                        mPenSurfaceView.update();
                        imageBitmap.recycle();
                        return true;
//                     When touching the screen in Insert ObjectTextBox mode.
                    } else if (mPenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_TEXT) {
                        // Specify the location where ObjectTextBox is inserted and add PageDoc.
                        SpenObjectTextBox textObj = new SpenObjectTextBox();
                        RectF rect1 = getRealPoint(event.getX(), event.getY(), 0, 0);
                        rect1.right += 200;
                        rect1.bottom += 50;
                        textObj.setRect(rect1, true);
                        mPenPageDoc.appendObject(textObj);
                        mPenPageDoc.selectObject(textObj);
                        mPenSurfaceView.update();
                    }
                }
            }
            return false;
        };
    }

    protected void createObjectRuntime() {
        if (mSpenObjectRuntimeInfoList == null || mSpenObjectRuntimeInfoList.size() == 0) {
            return;
        }
        try {
            for (SpenObjectRuntimeInfo info : mSpenObjectRuntimeInfoList) {
                if (info.name.equalsIgnoreCase("Video")) {
                    mVideoRuntime = mSpenObjectRuntimeManager.createObjectRuntime(info);
                    mObjectRuntimeInfo = info;
                    startObjectRuntime();
                    return;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startObjectRuntime() {
        if (mVideoRuntime == null) {
            return;
        }
        SpenObjectBase objectBase = null;
        switch (mVideoRuntime.getType()) {
            case SpenObjectRuntimeInterface.TYPE_NONE:
                return;
            case SpenObjectRuntimeInterface.TYPE_IMAGE:
                objectBase = new SpenObjectImage();
                break;
            case SpenObjectRuntimeInterface.TYPE_STROKE:
                objectBase = new SpenObjectStroke();
                break;
            case SpenObjectRuntimeInterface.TYPE_CONTAINER:
                objectBase = new SpenObjectContainer();
                break;
            default:
                break;
        }
        if (objectBase == null) {
//            Toast.makeText(this, "Has no selected object.", Toast.LENGTH_SHORT).show();
            return;
        }
        objectBase.setSorInfo(mObjectRuntimeInfo.className);
        objectBase.setOutOfViewEnabled(false);
        mVideoRuntime.setListener(objectRuntimeListener);
        mPenPageDoc.appendObject(objectBase);
        mPenPageDoc.selectObject(objectBase);
        mPenSurfaceView.update();
        mPenSurfaceView.getControl().setContextMenuVisible(false);
        mVideoRuntime.start(objectBase,
                new RectF(0, 0, mPenPageDoc.getWidth(), mPenPageDoc.getHeight()),
                mPenSurfaceView.getPan(), mPenSurfaceView.getZoomRatio(),
                mPenSurfaceView.getFrameStartPosition(), penViewLayout);
    }

}
