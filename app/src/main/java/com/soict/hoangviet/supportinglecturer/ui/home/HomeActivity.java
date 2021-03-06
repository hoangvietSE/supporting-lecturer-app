package com.soict.hoangviet.supportinglecturer.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.youtube.YouTube;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.adapter.HomeViewPagerAdapter;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.entity.response.FacebookResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.ui.login.LoginActivity;
import com.soict.hoangviet.supportinglecturer.ui.teacher.TeacherActivity;
import com.soict.hoangviet.supportinglecturer.ui.video.VideoActivity;
import com.soict.hoangviet.supportinglecturer.ui.youtube.YoutubeActivity;
import com.soict.hoangviet.supportinglecturer.utils.CommonExtensionUtil;
import com.soict.hoangviet.supportinglecturer.utils.Define;
import com.soict.hoangviet.supportinglecturer.utils.PermissionUtil;
import com.soict.hoangviet.supportinglecturer.utils.ToastUtil;
import com.soict.hoangviet.supportinglecturer.youtube.EventData;
import com.soict.hoangviet.supportinglecturer.youtube.YouTubeApi;
import com.soict.hoangviet.supportinglecturer.youtube.YouTubeNewSingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator3;

public class HomeActivity extends BaseActivity implements HomeView, View.OnClickListener {
    @Inject
    HomePresenter<HomeView> mPresenter;
    @Inject
    ISharePreference mSharePreference;
    public static final String TAG = HomeActivity.class.getSimpleName();
    public static final int REQUEST_CODE_PERMISSION = 9001;
    @BindView(R.id.llHomeProfile)
    ConstraintLayout llProfile;
    @BindView(R.id.llLiveStream)
    ConstraintLayout llLiveStream;
    @BindView(R.id.llCreateVideo)
    ConstraintLayout llCreateVideo;
//    @BindView(R.id.tvHomeSignIn)
//    TextView tvSignIn;
//    @BindView(R.id.tvHomeName)
//    TextView tvName;
//    @BindView(R.id.ivHomeAvatar)
//    ImageView ivAvatar;
    @BindView(R.id.btnYoutube)
    ConstraintLayout btnYoutube;
    HomeViewPagerAdapter homeViewPagerAdapter;
    @BindView(R.id.vp_home)
    ViewPager2 vpHome;
    @BindView(R.id.circle_indicator)
    CircleIndicator3 circleIndicator;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    int currentItem = 0;

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
        initEvent();
        requestPermission();
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        Log.d("myLog", mFirebaseRemoteConfig.getString("welcome_message"));
        initViewPager();
    }

    private void initViewPager() {

        homeViewPagerAdapter = new HomeViewPagerAdapter(this, false);
        homeViewPagerAdapter.addModels(new ArrayList<>(Arrays.asList(
                R.drawable.ic_home_one,
                R.drawable.ic_home_two,
                R.drawable.ic_home_three,
                R.drawable.ic_home_four
        )), false);
        vpHome.setAdapter(homeViewPagerAdapter);
        vpHome.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        circleIndicator.setViewPager(vpHome);
        mPresenter.loopIndicator();
    }

    private void onAttachPresenter() {
        mPresenter.onAttach(this);
    }

    private void initEvent() {
        mPresenter.checkConnectedToNetwork(this);
        changeUI(mSharePreference.getLoginStatus());
        llLiveStream.setOnClickListener(this);
        llCreateVideo.setOnClickListener(this);
        llProfile.setOnClickListener(this);
    }

    private void requestPermission() {
        mPresenter.requestPermission(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        llLiveStream.setOnClickListener(this);
        llCreateVideo.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        btnYoutube.setOnClickListener(view -> {
            if (!mSharePreference.getLoginStatus() || !mSharePreference.getLoginStatusFromGoogle()) {
                ToastUtil.show(this, getString(R.string.dialog_syntax_login));
            } else {
                startActivity(new Intent(HomeActivity.this, YoutubeActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llLiveStream:
                if (!mSharePreference.getLoginStatus()) {
                    ToastUtil.show(this, getString(R.string.dialog_syntax_login));
//                    showCautionDialog(getString(R.string.dialog_syntax_login), "", liveDialog -> {
//                        liveDialog.dismiss();
//                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//                        startActivityForResult(intent, Define.RequestCode.REQUEST_LOGIN);
//                    });
                } else {
                    if (mSharePreference.getLoginStatusFromGoogle()) {
                        CreateLiveEventTask createLiveEventTask = new CreateLiveEventTask();
                        createLiveEventTask.execute(mSharePreference.getAccountNameGoogle());
                    } else {
                        mPresenter.getRtmpFacebookLive();
                    }
                }
                break;
            case R.id.llCreateVideo:
                Intent intentList = new Intent(HomeActivity.this, VideoActivity.class);
                startActivity(intentList);
                break;
            case R.id.llHomeProfile:
                mPresenter.onLoginButtonClick();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Define.RequestCode.REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                changeUI(mSharePreference.getLoginStatus());
            }
        } else if (requestCode == Define.RequestCode.REQUEST_RECOVERY_ACCOUNT) {
//            changeUI(AppPreferences.INSTANCE.getKeyBoolean(Constants.KeyPreference.IS_LOGINED));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults != null && grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        PermissionUtil.goToSettingPermission(this);
                        break;
                    }
                }
            }
        }
    }

    private void changeUI(boolean isLogin) {
        if (isLogin) {
            tvLogin.setText(getResources().getString(R.string.login_logout_title));
        } else {
            tvLogin.setText(getResources().getString(R.string.login_title));
        }
    }

    @Override
    public void goToLoginScreen() {
        Intent intentLogin = new Intent(HomeActivity.this, LoginActivity.class);
        startActivityForResult(intentLogin, Define.RequestCode.REQUEST_LOGIN);
    }

    @Override
    public void showConfirmLogout() {
        showConfirmDialog(getString(R.string.dialog_confirm_logout), "", liveDialog -> {
            if (mSharePreference.getLoginStatusFromFacebook()) {
                mPresenter.logoutFacebook();
            } else {
                mPresenter.logoutGoogle();
            }
            liveDialog.dismiss();
            mSharePreference.setLoginStatus(false);
            changeUI(false);
        });
    }

    @Override
    public void goToTeacherScreenLiveStream() {
        Intent intent = new Intent(HomeActivity.this, TeacherActivity.class);
        intent.putExtra(TeacherActivity.EXTRA_LIVESTREAM, true);
        startActivity(intent);
    }

    @Override
    public void showInfoFacebook(FacebookResponse facebookResponse) {
//        tvName.setText(facebookResponse.getName());
//        CommonExtensionUtil.loadImageUrl(ivAvatar, facebookResponse.getPicture().getData().getUrl());
        mSharePreference.setUserId(facebookResponse.getId());
    }

    @Override
    public void loopViewPager() {
        if (currentItem == homeViewPagerAdapter.getItemCount() - 1) {
            currentItem = 0;
        }
        vpHome.setCurrentItem(currentItem++);
    }

    private class CreateLiveEventTask extends AsyncTask<String, Void, EventData> {

        @Override
        protected void onPreExecute() {
            showLoading();
        }

        @Override
        protected EventData doInBackground(String... accounts) {
            try {
                YouTube youTube = YouTubeNewSingleton.newInstance(accounts[0], HomeActivity.this).getYoutube();
                String date = new Date().toString();
                YouTubeApi.createLiveEvent(youTube, "Event - " + date,
                        "A live streaming event - " + date);
                return YouTubeApi.getLiveCurrentEvent(youTube);
            } catch (UserRecoverableAuthIOException userRecoverableException) {
                Log.w(TAG, "getSubscription:recoverable exception", userRecoverableException);
                startActivityForResult(userRecoverableException.getIntent(), Define.RequestCode.REQUEST_RECOVERY_ACCOUNT);
            } catch (IOException e) {
                Log.w(TAG, "getSubscription:exception", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(EventData fetchedEvent) {
            hideLoading();
            if (fetchedEvent != null) {
                startStreaming(fetchedEvent);
            } else {
                Log.d(TAG, "subscriptions: null");
                Toast.makeText(HomeActivity.this, "Cant create event, you account had permission live stream ?? ", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class StartEventTask extends AsyncTask<String, Void, Void> {
        private ScheduledExecutorService mScheduleTaskExecutor;
        private String broadCastId;
        private String rtmpLink;
        private String streamId;

        @Override
        protected void onPreExecute() {
            showLoading();
        }

        @Override
        protected Void doInBackground(String... params) {
            broadCastId = params[0];
            streamId = params[1];
            rtmpLink = params[2];
            mScheduleTaskExecutor = Executors.newSingleThreadScheduledExecutor();
            YouTube youTube = YouTubeNewSingleton.newInstance(mSharePreference.getAccountNameGoogle(), HomeActivity.this).getYoutube();
            mScheduleTaskExecutor.scheduleAtFixedRate(() -> {
                try {
                    if (streamId != null) {
                        YouTubeApi.checkStreamStatus(youTube, broadCastId, streamId, mScheduleTaskExecutor);
                    }
                } catch (IOException e) {
                    Log.e(TAG, null, e);
                }
            }, 0, 2, TimeUnit.SECONDS);
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            hideLoading();
            mSharePreference.setLiveStreamStatus(true);
            mSharePreference.setRtmpGoogle(rtmpLink);
            mSharePreference.setBroadcastId(broadCastId);
            Intent intent = new Intent(HomeActivity.this, TeacherActivity.class);
            intent.putExtra(TeacherActivity.EXTRA_LIVESTREAM, true);
            startActivity(intent);
            startActivity(intent);
        }
    }

    public void startStreaming(EventData event) {
        String broadcastId = event.getId();
        String streamId = event.getStreamId();
        new StartEventTask().execute(broadcastId, streamId, event.getIngestionAddress());
    }

}
