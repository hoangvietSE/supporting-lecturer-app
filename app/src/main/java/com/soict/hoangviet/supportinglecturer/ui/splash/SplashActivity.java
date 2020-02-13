package com.soict.hoangviet.supportinglecturer.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.ui.home.HomeActivity;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends BaseActivity implements SplashView {
    @Inject
    SplashPresenter<SplashView> mPresenter;

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
        mPresenter.checkNetworkConnection();
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
        new Handler().postDelayed(new SplashRunnable(this), 1000);
    }

    private void showDialogAlert() {
        showCautionDialog(getResources().getString(R.string.splash_no_internet_connection_warning), "", liveDialog -> {
            liveDialog.dismiss();
            startActivity(new Intent(this, HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    private static class SplashRunnable implements Runnable {
        private final WeakReference<Activity> activityWeakReference;

        SplashRunnable(Activity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            Activity activity = activityWeakReference.get();
            activity.startActivity(new Intent(activity, HomeActivity.class));
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            activity.finish();
        }
    }
}
