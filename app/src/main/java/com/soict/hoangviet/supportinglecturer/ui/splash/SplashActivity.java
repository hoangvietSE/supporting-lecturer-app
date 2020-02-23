package com.soict.hoangviet.supportinglecturer.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.ui.home.HomeActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends BaseActivity implements SplashView {
    @Inject
    SplashPresenter<SplashView> mPresenter;
    private Timer timer;

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
        initHandler();
        mPresenter.checkNetworkConnection();
    }

    private void initHandler() {
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
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    public void onNetworkConnection(boolean hasNetwork) {
        if (hasNetwork) {
            goToHomeScreen();
        } else {
            showDialogAlert();
        }
    }

    private void goToHomeScreen() {
        timer = new Timer();
        timer.schedule(new SplashTimerTask(this), 2000);
    }

    private void showDialogAlert() {
        showCautionDialog(getResources().getString(R.string.splash_no_internet_connection_warning), "", liveDialog -> {
            liveDialog.dismiss();
            startActivity(new Intent(this, HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    private static class SplashTimerTask extends TimerTask {
        private final WeakReference<Activity> activityWeakReference;

        SplashTimerTask(Activity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            Activity activity = activityWeakReference.get();
            activity.startActivity(new Intent(activity, HomeActivity.class));
            activity.finish();
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) timer.cancel();
    }
}
