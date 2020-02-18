package com.soict.hoangviet.supportinglecturer.ui.video;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.adapter.VideoAdapter;
import com.soict.hoangviet.supportinglecturer.base.BaseRecyclerView;
import com.soict.hoangviet.supportinglecturer.entity.response.VideoResponse;
import com.soict.hoangviet.supportinglecturer.eventbus.RecordSuccessEvent;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.ui.edit.EditActivity;
import com.soict.hoangviet.supportinglecturer.ui.teacher.TeacherActivity;
import com.soict.hoangviet.supportinglecturer.utils.Define;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoActivity extends BaseActivity implements VideoView {

    @Inject
    VideoPresenter<VideoView> mPresenter;
    public static final String TAG = VideoActivity.class.getSimpleName();
    public static final int EDIT_VIDEO_RESULT = 1001;
    public static final int EDIT_VIDEO_REQUEST = 1000;
    private VideoAdapter videoAdapter;
    @BindView(R.id.tbVideo)
    Toolbar tbVideo;
    @BindView(R.id.fabHome)
    FloatingActionButton fabHome;
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
                openVideo(videoAdapter.getItem(position, VideoResponse.class));
            }

            @Override
            public void onItemCropVideo(int position) {
                Intent intent = new Intent(VideoActivity.this, EditActivity.class);
                intent.putExtra(Define.IntentKey.EXTRA_VIDEO_URL, videoAdapter.getItem(position, VideoResponse.class).getVideoPath());
                startActivityForResult(intent, EDIT_VIDEO_REQUEST);
            }

            @Override
            public void onItemShareVideo(int position) {
                VideoResponse videoResponse = videoAdapter.getItem(position, VideoResponse.class);
                Uri uri = FileProvider.getUriForFile(VideoActivity.this, getApplicationContext().getPackageName() + ".provider", new File(videoResponse.getVideoPath()));
                Intent videoShare = new Intent(Intent.ACTION_SEND);
                videoShare.setType("*/*");
                videoShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                videoShare.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(videoShare);
            }

            @Override
            public void onItemDeleteVideo(int position) {
                deleteVideo(videoAdapter.getItem(position, VideoResponse.class), position);
            }
        }, false);
        rvVideo.setAdapter(videoAdapter);
        rvVideo.setOnRefreshListener(() -> {
            mPresenter.fetchVideo(true);
        });
        rvVideo.setListLayoutManager(LinearLayoutManager.VERTICAL);
    }

    private void deleteVideo(VideoResponse item, int position) {
        showConfirmDialog("Bạn muốn xóa video " + item.getVideoName() + " khỏi hệ thống?", "", liveDialog -> {
            File fileDelete = new File(item.getVideoPath());
            if (fileDelete.exists()) {
                fileDelete.delete();
            }
            videoAdapter.removeModel(position);
            liveDialog.dismiss();
        });
    }

    private void openVideo(VideoResponse item) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getVideoPath()));
        intent.setDataAndType(Uri.parse(item.getVideoPath()), "video/mp4");
        startActivity(intent);
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
        fabHome.setOnClickListener(view -> {
            Intent intent = new Intent(VideoActivity.this, TeacherActivity.class);
            startActivity(intent);
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_VIDEO_REQUEST && resultCode == EDIT_VIDEO_RESULT) {
            mPresenter.fetchVideo(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(RecordSuccessEvent event) {
        /* Do something */
        mPresenter.fetchVideo(true);
        EventBus.getDefault().removeStickyEvent(event);
    }
}
