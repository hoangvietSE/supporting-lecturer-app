package com.soict.hoangviet.supportinglecturer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.base.EndlessLoadingRecyclerViewAdapter;
import com.soict.hoangviet.supportinglecturer.entity.response.VideoResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends EndlessLoadingRecyclerViewAdapter {
    private OnClickVideo listener;

    public VideoAdapter(Context context, OnClickVideo listener, boolean enableSelectedMode) {
        super(context, enableSelectedMode);
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
        //        @BindView(R.id.tvItemHomeDetail)
//        TextView tvShare;
        @BindView(R.id.tvItemVideoDelete)
        TextView tvDelete;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(VideoResponse data) {
            tvName.setText(data.getVideoName());
            tvDetail.setText("13/02/2020 15:30:45");
            tvVideoDetail.setOnClickListener(view -> {
                listener.onItemWatchVideo(getAdapterPosition());
            });
            tvEdit.setOnClickListener(view -> {
                listener.onItemCropVideo(getAdapterPosition());
            });
            tvDelete.setOnClickListener(view -> {
                listener.onItemDeleteVideo(getAdapterPosition());
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
