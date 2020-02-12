package com.soict.hoangviet.supportinglecturer.custom;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soict.hoangviet.supportinglecturer.utils.ViewUtils;

public abstract class BaseDecoration extends RecyclerView.ItemDecoration {
    private int paddingTop;
    private int paddingStart;
    private int paddingEnd;
    private int paddingBottom;

    public BaseDecoration(float padding) {
        this.paddingTop = ViewUtils.dpToPx(padding);
        this.paddingStart = ViewUtils.dpToPx(padding);
        this.paddingEnd = ViewUtils.dpToPx(padding);
        this.paddingBottom = ViewUtils.dpToPx(padding);
    }

    public BaseDecoration(float paddingTop, float paddingStart) {
        this.paddingTop = ViewUtils.dpToPx(paddingTop);
        this.paddingBottom = ViewUtils.dpToPx(paddingTop);
        this.paddingStart = ViewUtils.dpToPx(paddingStart);
        this.paddingEnd = ViewUtils.dpToPx(paddingStart);
    }

    public BaseDecoration(float paddingTop, float paddingStart, float paddingEnd, float paddingBottom) {
        this.paddingTop = ViewUtils.dpToPx(paddingTop);
        this.paddingStart = ViewUtils.dpToPx(paddingStart);
        this.paddingEnd = ViewUtils.dpToPx(paddingEnd);
        this.paddingBottom = ViewUtils.dpToPx(paddingBottom);
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingStart() {
        return paddingStart;
    }

    public void setPaddingStart(int paddingStart) {
        this.paddingStart = paddingStart;
    }

    public int getPaddingEnd() {
        return paddingEnd;
    }

    public void setPaddingEnd(int paddingEnd) {
        this.paddingEnd = paddingEnd;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        setupOutRect(outRect, view, parent, state);
    }

    abstract void setupOutRect(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state);
}
