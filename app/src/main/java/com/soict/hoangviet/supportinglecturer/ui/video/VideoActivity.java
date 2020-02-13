package com.soict.hoangviet.supportinglecturer.ui.video;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoActivity extends BaseActivity implements VideoView {

    @Inject
    VideoPresenter<VideoView> mPresenter;
    public static final String TAG = VideoActivity.class.getSimpleName();

    @BindView(R.id.tbVideo) Toolbar tbVideo;
    @BindView(R.id.fabHome) FloatingActionButton fabCall;
    @BindView(R.id.srlVideos) SwipeRefreshLayout srlVideo;
    @BindView(R.id.rvVideos) RecyclerView rvVideo;

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(tbVideo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.tv_title_action_bar);
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
        return R.layout.activity_videos;
    }
}
