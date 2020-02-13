package com.soict.hoangviet.supportinglecturer.ui.video;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.adapter.VideoAdapter;
import com.soict.hoangviet.supportinglecturer.base.BaseRecyclerView;
import com.soict.hoangviet.supportinglecturer.entity.response.VideoResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoActivity extends BaseActivity implements VideoView {

    @Inject
    VideoPresenter<VideoView> mPresenter;
    public static final String TAG = VideoActivity.class.getSimpleName();
    private VideoAdapter videoAdapter;
    @BindView(R.id.tbVideo)
    Toolbar tbVideo;
    @BindView(R.id.fabHome)
    FloatingActionButton fabCall;
    @BindView(R.id.srlVideos)
    SwipeRefreshLayout srlVideo;
    @BindView(R.id.rvVideos)
    BaseRecyclerView rvVideo;

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
        initToolbar();
        initVideoAdapter();
    }

    private void initVideoAdapter() {
        videoAdapter = new VideoAdapter(this, new VideoAdapter.OnClickVideo() {
            @Override
            public void onItemWatchVideo(int position) {

            }

            @Override
            public void onItemCropVideo(int position) {

            }

            @Override
            public void onItemShareVideo(int position) {

            }

            @Override
            public void onItemDeleteVideo(int position) {

            }
        }, false);
        rvVideo.setAdapter(videoAdapter);
        rvVideo.setOnRefreshListener(() -> {
            mPresenter.fetchVideo(true);
        });
        rvVideo.setListLayoutManager(LinearLayoutManager.VERTICAL);
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
        mPresenter.fetchVideo(false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_videos;
    }

    @Override
    public void showListVideo(List<VideoResponse> videoResponseList, boolean isRefresh) {
        if (isRefresh) {
            rvVideo.refresh(videoResponseList);
        } else {
            rvVideo.addItem(videoResponseList);
        }
    }
}
