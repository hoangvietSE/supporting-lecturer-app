package com.soict.hoangviet.supportinglecturer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.base.EndlessLoadingRecyclerViewAdapter;
import com.soict.hoangviet.supportinglecturer.entity.response.VideoResponse;
import com.soict.hoangviet.supportinglecturer.utils.DateUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends EndlessLoadingRecyclerViewAdapter {
    private OnClickVideo listener;

    public VideoAdapter(Context context, OnClickVideo listener, boolean enableSelectedMode) {
        super(context, enableSelectedMode);
        this.listener = listener;
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(View itemView, ViewGroup parent) {
        return new VideoViewHolder(itemView);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        holder.bind(getItem(position, VideoResponse.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_home_video;
    }

    public class VideoViewHolder extends NormalViewHolder<VideoResponse> {
        @BindView(R.id.tvItemHome)
        TextView tvName;
        @BindView(R.id.tvItemHomeDetail)
        TextView tvDetail;
        @BindView(R.id.llItemVideoContent)
        LinearLayout tvVideoDetail;
        @BindView(R.id.tvItemVideoCrop)
        TextView tvEdit;
        @BindView(R.id.tvItemVideoShare)
        TextView tvShare;
        @BindView(R.id.tvItemVideoDelete)
        TextView tvDelete;
        @BindView(R.id.swipeRevealLayout)
        SwipeRevealLayout swipeRevealLayout;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(VideoResponse data) {
            tvName.setText(data.getVideoName());
            tvDetail.setText(DateUtil.getStampByDate(new Date(data.getVideoDate()), DateUtil.DATE_FORMAT_19));
            tvVideoDetail.setOnClickListener(view -> {
                listener.onItemWatchVideo(getAdapterPosition());
            });
            if (swipeRevealLayout.isOpened()) {
                swipeRevealLayout.close(true);
            }
            tvEdit.setOnClickListener(view -> {
                listener.onItemCropVideo(getAdapterPosition());
            });
            tvDelete.setOnClickListener(view -> {
                listener.onItemDeleteVideo(getAdapterPosition());
            });
            tvShare.setOnClickListener(view -> {
                listener.onItemShareVideo(getAdapterPosition());
            });
        }
    }

    public interface OnClickVideo {

        void onItemWatchVideo(int position);

        void onItemCropVideo(int position);

        void onItemShareVideo(int position);

        void onItemDeleteVideo(int position);
    }
}
