package com.soict.hoangviet.supportinglecturer.ui.edit;

import android.net.Uri;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.utils.Define;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

public class EditActivity extends BaseActivity implements EditView {

    @Inject
    EditPresenter<EditView> mPresenter;
    @BindView(R.id.timeLine)
    K4LVideoTrimmer videoTrimmer;

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
        initVideoTrimmer();
    }

    private void initVideoTrimmer() {
        if (videoTrimmer != null) {
            videoTrimmer.setMaxDuration(10000000);
            videoTrimmer.setDestinationPath(getIntent().getStringExtra(Define.IntentKey.EXTRA_VIDEO_URL));
            videoTrimmer.setVideoURI(Uri.parse(getIntent().getStringExtra(Define.IntentKey.EXTRA_VIDEO_URL)));
            videoTrimmer.setOnTrimVideoListener(new OnTrimVideoListener() {
                @Override
                public void getResult(Uri uri) {
                    finish();
                }

                @Override
                public void cancelAction() {
                    finish();
                }
            });
        }
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
        return R.layout.activity_edit_video;
    }
}
