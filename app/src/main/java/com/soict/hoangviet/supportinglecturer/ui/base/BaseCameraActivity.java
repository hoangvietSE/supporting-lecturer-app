package com.soict.hoangviet.supportinglecturer.ui.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.filter.NoFilter;
import com.soict.hoangviet.supportinglecturer.BuildConfig;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.adapter.FilterImageAdapter;
import com.soict.hoangviet.supportinglecturer.customview.AutoFitTextureView;
import com.soict.hoangviet.supportinglecturer.customview.MovableFloatingActionButton;
import com.soict.hoangviet.supportinglecturer.customview.OnDragTouchListener;
import com.soict.hoangviet.supportinglecturer.customview.SonnyJackDragView;
import com.soict.hoangviet.supportinglecturer.utils.CameraEnum;
import com.soict.hoangviet.supportinglecturer.utils.DeviceUtil;
import com.soict.hoangviet.supportinglecturer.utils.LogUtil;
import com.soict.hoangviet.supportinglecturer.utils.PermissionUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseCameraActivity extends BaseActivity {
    private static final int REQUEST_CHOOSE_IMAGE = 9001;
    private static final int REQUEST_IMAGE_CAPTURE = 9002;
    private static final int REQUEST_PERMISSION_GALLERY = 9003;
    private static final int REQUEST_PERMISSION_CAPTURE_CAMERA = 9004;
    private static final int REQUEST_SETTING = 9005;
    private static final int CAMERA_BACK = 0;
    private static final int CAMERA_FONT = 1;

    @BindView(R.id.textureView)
    protected AutoFitTextureView textureView;
    @BindView(R.id.cv_camera)
    protected CardView cardView;
    @BindView(R.id.drawView)
    protected RelativeLayout drawView;
    @Nullable
    @BindView(R.id.rl_camera)
    protected RelativeLayout rlCamera;
    @Nullable
    @BindView(R.id.rcvFilter)
    protected RecyclerView rcvFilter;
    @Nullable
    @BindView(R.id.camera)
    protected CameraView cameraView;
    //    @Nullable
//    @BindView(R.id.camLoading)
//    protected RelativeLayout camLoading;
//    @Nullable
//    @BindView(R.id.frameCamera)
//    protected FrameLayout frameCamera;
    private SonnyJackDragView mSonnyJackDragView;
    private CameraDevice.StateCallback stateCallback;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder captureRequestBuilder;
    private CameraCaptureSession cameraCaptureSessions;
    private Size imageDimension;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    TextureView.SurfaceTextureListener textureListener;
    private String cameraId;
    CameraCharacteristics characteristics;
    static final int REQUEST_CAMERA_PERMISSION = 1009;
    private int lastAction;
    private float dX;
    private float dY;
    private int screenHight;
    private int screenWidth;
    int effect = 0;
    //LANDSCAPE
    @Nullable
    @BindView(R.id.mfa_left_right)
    MovableFloatingActionButton mfaLeftRight;
    protected CameraEnum mCameraEnum;
    protected boolean isShowCamera = true;
    private FilterImageAdapter filterImageAdapter;
    private String cameraFilePath;
    private File mFileCreateImage;
    private Uri mPhotoCaptureUri;
    private CameraListener cameraListener;

    @Override
    protected void initView() {
        cameraListener = createCameraListener();
        cameraView.setLifecycleOwner(this);
        initStateCallback();
        initTextureListener();
        initEnum();
        initFilterAdapter();
        cardView.setOnTouchListener(new OnDragTouchListener(cardView));
    }

    protected abstract CameraListener createCameraListener();

    private void initFilterAdapter() {
        if (DeviceUtil.isLandscape(this)) {
            filterImageAdapter = new FilterImageAdapter(this, false, data -> {
                try {
                    cameraView.setFilter(data.getFilterClass().newInstance());
                } catch (IllegalAccessException e) {
                    cameraView.setFilter(new NoFilter());
                } catch (InstantiationException e) {
                    cameraView.setFilter(new NoFilter());
                }
            });
            rcvFilter.setAdapter(filterImageAdapter);
            rcvFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }
    }

//    private void hideFrameCamera() {
//        frameCamera.setVisibility(View.GONE);
//    }

//    private void showFrameCamera() {
//        frameCamera.setVisibility(View.VISIBLE);
//    }

    private void initEnum() {
        mCameraEnum = CameraEnum.SHOW_HALF_CAMERA;
    }

    @Override
    protected void initListener() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            hideFrameCamera();
            mfaLeftRight.setOnClickListener(view -> {
                if (!avoidDuplicateClick()) {
                    if (rlCamera.getVisibility() == View.VISIBLE) {
                        rlCamera.setVisibility(View.GONE);
                    } else {
                        rlCamera.setVisibility(View.VISIBLE);
                    }
//                    switch (mCameraEnum) {
//                        case SHOW_HALF_CAMERA:
//                            hideFrameCamera();
//                            rlCamera.setVisibility(View.GONE);
//                            mCameraEnum = CameraEnum.HIDE_CAMERA;
//                            isShowCamera = false;
//                            break;
//                        case HIDE_CAMERA:
//                            showFrameCamera();
//                            mCameraEnum = CameraEnum.SHOW_FULL_CAMERA;
//                            showTextureFull();
//                            break;
//                        case SHOW_FULL_CAMERA:
//                            hideFrameCamera();
//                            showTextureViewHalf();
//                            rlCamera.setVisibility(View.VISIBLE);
//                            isShowCamera = true;
//                            mCameraEnum = CameraEnum.SHOW_HALF_CAMERA;
//                            break;
//                    }
                }
            });
        } else {
            initOnTouch();
        }
    }

    @Override
    protected void initData() {

    }

    private void initOnTouch() {
        mSonnyJackDragView = new SonnyJackDragView.Builder()
                .setActivity(this)
                .setNeedNearEdge(true)
                .setView(cameraView)
                .build();
    }

    private void initStateCallback() {
        stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                cameraDevice = camera;
                createCameraPreview();
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                cameraDevice.close();
            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, int i) {
                cameraDevice.close();
                cameraDevice = null;
            }
        };
    }

    private void initTextureListener() {
        textureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                DeviceUtil.transformImage(BaseCameraActivity.this, textureView, width, height);
                openCamera(CAMERA_FONT);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        };

    }

    private void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_EFFECT_MODE_AQUA);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if (cameraDevice == null)
                        return;
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if (cameraDevice == null)
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startBackgroundThread();
        if (textureView.isAvailable()) {
            DeviceUtil.transformImage(this, textureView, textureView.getWidth(), textureView.getHeight());
            openCamera(CAMERA_FONT);
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    protected void onPause() {
//        stopBackgroundThread();
        super.onPause();
    }

    private void openCamera(int rotate) {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = manager.getCameraIdList()[rotate];
            characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            //Check realtime permission if run higher API 23
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Vui lòng cấp quyền truy cập camera", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        for (String permission : permissions) {
            if (PermissionUtil.hasPermission(permission)) {
                switch (requestCode) {
                    case REQUEST_PERMISSION_GALLERY:
                        LogUtil.d("Accept Permission: GALLERY");
                        pickImageFromGallery();
                        break;
                    case REQUEST_PERMISSION_CAPTURE_CAMERA:
                        LogUtil.d("Accept Permission: CAMERA");
                        dispatchTakePictureIntent();
                        break;
                }
            } else {
                LogUtil.d("Denied: Permission");
                PermissionUtil.goToSettingPermission(this);
            }
        }
    }

//    protected void showTextureFull() {
//        FrameLayout.LayoutParams layoutParams = createFrameLayoutParams(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT
//        );
//        removeViewParent(textureView);
//        frameCamera.addView(textureView, layoutParams);
//    }

    @SuppressLint("CheckResult")
    protected void showTextureViewHalf() {
        RelativeLayout.LayoutParams layoutParams = createRelativeLayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                DeviceUtil.convertDpToPx(this, 450)
        );
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//        textureView.setOnTouchListener(null);
        removeViewParent(textureView);
        Completable.timer(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
//                    camLoading.setVisibility(View.VISIBLE);
                })
                .doFinally(() -> {
//                    camLoading.setVisibility(View.GONE);
                })
                .subscribe(() -> {
                    rlCamera.addView(textureView, layoutParams);
                });
    }

    private RelativeLayout.LayoutParams createRelativeLayoutParams(int width, int height) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        return layoutParams;
    }

    private FrameLayout.LayoutParams createFrameLayoutParams(int width, int height) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        return layoutParams;
    }

    private void removeViewParent(AutoFitTextureView view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).endViewTransition(view);
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    protected void openCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LogUtil.d("Above Android LOLLIPOP");
            if (PermissionUtil.hasPermission(android.Manifest.permission.CAMERA)) {
                dispatchTakePictureIntent();
            } else {
                LogUtil.d("Request: Permission Camera");
                PermissionUtil.requestPermission(
                        this,
                        new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_PERMISSION_CAPTURE_CAMERA
                );
            }
        } else {
            LogUtil.d("Below Android LOLLIPOP");
            dispatchTakePictureIntentPreLollipop();
        }
    }

    protected void openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (PermissionUtil.hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                pickImageFromGallery();
            } else {
                PermissionUtil.requestPermission(
                        this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_GALLERY
                );
            }
        } else {
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = new String[]{"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, REQUEST_CHOOSE_IMAGE);
    }

    private void dispatchTakePictureIntentPreLollipop() {

    }

    private void dispatchTakePictureIntent() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentCamera.resolveActivity(getPackageManager()) != null) {
            try {
                mFileCreateImage = createImageFile();
                mPhotoCaptureUri = FileProvider.getUriForFile(
                        this,
                        getResources().getString(R.string.file_provider),
                        mFileCreateImage
                );
                intentCamera.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        mPhotoCaptureUri
                );
                startActivityForResult(intentCamera, REQUEST_IMAGE_CAPTURE);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        cameraFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            if (cameraFilePath != null) {
                LogUtil.d("Success: Capture Image");
                if (cameraListener != null) {
                    cameraListener.onTakeImageFileCaptureSuccess(cameraFilePath, mPhotoCaptureUri);
                }
            }
            return;
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_CHOOSE_IMAGE && data != null) {
            LogUtil.d("Success: Pick Image");
            handleImageUri(data.getData());
            return;
        }
    }

    private void handleImageUri(Uri uriImage) {
        String[] filePathColumn = new String[]{MediaStore.Images.Media.DATA};
        // Get the cursor
        Cursor cursor = getContentResolver().query(uriImage, filePathColumn, null, null, null, null);
        // Move to first row
        cursor.moveToFirst();
        //Get the column index of MediaStore.Images.Media.DATA
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        //Gets the String value in the column
        String imgDecodableString = cursor.getString(columnIndex);
        cameraListener.onTakeAbsolutePathImageSuccess(imgDecodableString, uriImage);
        cursor.close();
    }

    public interface CameraListener {
        void onTakeImageFileCaptureSuccess(String cameraFilePath, Uri uriImage);

        void onTakeAbsolutePathImageSuccess(String absoluteFilePathImage, Uri uriImage);
    }
}
