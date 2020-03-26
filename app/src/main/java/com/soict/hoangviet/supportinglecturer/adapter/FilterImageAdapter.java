package com.soict.hoangviet.supportinglecturer.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.otaliastudios.cameraview.filter.NoFilter;
import com.otaliastudios.cameraview.filters.BlackAndWhiteFilter;
import com.otaliastudios.cameraview.filters.BrightnessFilter;
import com.otaliastudios.cameraview.filters.ContrastFilter;
import com.otaliastudios.cameraview.filters.CrossProcessFilter;
import com.otaliastudios.cameraview.filters.DocumentaryFilter;
import com.otaliastudios.cameraview.filters.DuotoneFilter;
import com.otaliastudios.cameraview.filters.FillLightFilter;
import com.otaliastudios.cameraview.filters.GammaFilter;
import com.otaliastudios.cameraview.filters.GrayscaleFilter;
import com.otaliastudios.cameraview.filters.HueFilter;
import com.otaliastudios.cameraview.filters.LomoishFilter;
import com.otaliastudios.cameraview.filters.SepiaFilter;
import com.otaliastudios.cameraview.filters.VignetteFilter;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.base.EndlessLoadingRecyclerViewAdapter;
import com.soict.hoangviet.supportinglecturer.entity.response.ImageFilter;
import com.soict.hoangviet.supportinglecturer.utils.CommonExtensionUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterImageAdapter extends EndlessLoadingRecyclerViewAdapter {
    private ArrayList<ImageFilter> listFilter = new ArrayList<>();
    private OnFilterClick listener;

    public FilterImageAdapter(Context context, boolean enableSelectedMode, OnFilterClick listener) {
        super(context, enableSelectedMode);
        this.listener = listener;
        listFilter.add(new ImageFilter(R.drawable.filter_none, true, "None", NoFilter.class));
        listFilter.add(new ImageFilter(R.drawable.black_white, false, "Black & White", BlackAndWhiteFilter.class));
        listFilter.add(new ImageFilter(R.drawable.brightness, false, "Brightness", BrightnessFilter.class));
        listFilter.add(new ImageFilter(R.drawable.constrast, false, "Black & White", ContrastFilter.class));
        listFilter.add(new ImageFilter(R.drawable.cross_process, false, "Black & White", CrossProcessFilter.class));
        listFilter.add(new ImageFilter(R.drawable.documentary, false, "Black & White", DocumentaryFilter.class));
        listFilter.add(new ImageFilter(R.drawable.duotone, false, "Black & White", DuotoneFilter.class));
        listFilter.add(new ImageFilter(R.drawable.fill_light, false, "Black & White", FillLightFilter.class));
        listFilter.add(new ImageFilter(R.drawable.gam_ma, false, "Black & White", GammaFilter.class));
        listFilter.add(new ImageFilter(R.drawable.grey_scale, false, "Black & White", GrayscaleFilter.class));
        listFilter.add(new ImageFilter(R.drawable.filter_hue, false, "Black & White", HueFilter.class));
        listFilter.add(new ImageFilter(R.drawable.lomo_ish, false, "Black & White", LomoishFilter.class));
        listFilter.add(new ImageFilter(R.drawable.filter_sepia, false, "Black & White", SepiaFilter.class));
        listFilter.add(new ImageFilter(R.drawable.filter_vignete, false, "Black & White", VignetteFilter.class));
        addModels(listFilter, false);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(View itemView, ViewGroup parent) {
        return new FilterViewHolder(itemView);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        holder.bind(getItem(position, ImageFilter.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_filter_camera;
    }

    class FilterViewHolder extends NormalViewHolder<ImageFilter> {
        @BindView(R.id.imv_filter)
        ImageView imvFilter;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.imv_tick)
        ImageView imvTick;

        public FilterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(ImageFilter data) {
            CommonExtensionUtil.loadImageDrawable(imvFilter, data.getImage());
            tvName.setText(data.getName());
            if (data.isSelected()) {
                imvTick.setVisibility(View.VISIBLE);
            } else {
                imvTick.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(view -> {
                for (int i = 0; i < listFilter.size(); i++) {
                    if (i == getAdapterPosition()) {
                        listFilter.get(i).setSelected(true);
                    } else {
                        listFilter.get(i).setSelected(false);
                    }
                }
                notifyDataSetChanged();
                listener.onClick(data);
            });
        }
    }

    public interface OnFilterClick {
        void onClick(ImageFilter data);
    }
}
