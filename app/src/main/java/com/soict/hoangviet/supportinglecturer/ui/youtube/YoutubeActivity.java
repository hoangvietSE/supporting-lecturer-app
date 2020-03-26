package com.soict.hoangviet.supportinglecturer.ui.youtube;

import android.app.Dialog;
import android.view.View;

import androidx.annotation.NonNull;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.adapter.YoutubeAdapter;
import com.soict.hoangviet.supportinglecturer.base.BaseRecyclerView;
import com.soict.hoangviet.supportinglecturer.entity.response.youtube.ItemsItem;
import com.soict.hoangviet.supportinglecturer.entity.response.youtube.YoutubeVideoResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.utils.DialogUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class YoutubeActivity extends BaseActivity implements YoutubeView {

    @Inject
    YoutubePresenter<YoutubeView> mPresenter;
    @BindView(R.id.brv_list_youtube)
    BaseRecyclerView brvListYoutube;
    private YoutubeAdapter youtubeAdapter;

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
        initAdapter();
    }

    private void initAdapter() {
        youtubeAdapter = new YoutubeAdapter(this, false, data -> {
            DialogUtil.showContentDialog(
                    this,
                    R.layout.layout_dialog_youtube,
                    true,
                    data,
                    new DialogUtil.OnAddDataToDialogListener() {
                        @Override
                        public <T> void onData(Dialog dialog, View view, T data) {
                            ((YouTubePlayerView) view.findViewById(R.id.youtube_player_view)).addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    youTubePlayer.loadVideo(((ItemsItem) data).getId().getVideoId(), 0);
                                }
                            });
                        }
                    }
            );
        });
        youtubeAdapter.enableLoadingMore(false);
        brvListYoutube.setAdapter(youtubeAdapter);
        brvListYoutube.setGridLayoutManager(2);
        brvListYoutube.setOnRefreshListener(() -> {
            fetchVideoYoutube(true);
        });
    }

    private void onAttachPresenter() {
        mPresenter.onAttach(this);
    }

    @Override
    protected void initData() {
        fetchVideoYoutube(false);
    }

    private void fetchVideoYoutube(boolean isRefresh) {
        mPresenter.fetchListVideoYoutube(isRefresh);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_youtube;
    }

    @Override
    public void showListVideoYoutube(YoutubeVideoResponse response, boolean isRefresh) {
        if (isRefresh) {
            brvListYoutube.refresh(response.getItems());
        } else {
            brvListYoutube.addItem(response.getItems());
        }
    }
}
