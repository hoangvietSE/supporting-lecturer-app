package com.soict.hoangviet.supportinglecturer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.base.EndlessLoadingRecyclerViewAdapter;
import com.soict.hoangviet.supportinglecturer.entity.response.youtube.ItemsItem;
import com.soict.hoangviet.supportinglecturer.module.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeAdapter extends EndlessLoadingRecyclerViewAdapter {
    private OnItemWatch listener;

    public YoutubeAdapter(Context context, boolean enableSelectedMode, OnItemWatch listener) {
        super(context, enableSelectedMode);
        this.listener = listener;
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(View itemView, ViewGroup parent) {
        return new YoutubeViewHolder(itemView);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        holder.bind(getItem(position, ItemsItem.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_youtube;
    }

    class YoutubeViewHolder extends NormalViewHolder<ItemsItem> {
        @BindView(R.id.guide)
        Guideline guide;
        @BindView(R.id.imv_video)
        ImageView imvVideo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public YoutubeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(ItemsItem data) {
            GlideApp.with(itemView.getContext())
                    .load(data.getSnippet().getThumbnails().getHigh().getUrl())
                    .placeholder(R.drawable.background_default)
                    .error(R.drawable.background_default)
                    .into(imvVideo);
            tvName.setText(data.getSnippet().getTitle());
            tvTime.setText(data.getSnippet().getPublishedAt());
            itemView.setOnClickListener(view -> {
                listener.onClick(data);
            });
        }
    }

    public interface OnItemWatch {
        void onClick(ItemsItem data);
    }
}
