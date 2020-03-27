package com.soict.hoangviet.supportinglecturer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.base.EndlessLoadingRecyclerViewAdapter;
import com.soict.hoangviet.supportinglecturer.utils.CommonExtensionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeViewPagerAdapter extends EndlessLoadingRecyclerViewAdapter {
    public HomeViewPagerAdapter(Context context, boolean enableSelectedMode) {
        super(context, enableSelectedMode);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(View itemView, ViewGroup parent) {
        return new HomeViewHolder(itemView);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        holder.bind(getItem(position, Integer.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_vp_home;
    }

    class HomeViewHolder extends NormalViewHolder<Integer> {
        @BindView(R.id.imv_item)
        ImageView imvItem;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(Integer data) {
            CommonExtensionUtil.loadImageDrawable(imvItem, data);
        }
    }
}
