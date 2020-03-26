package com.soict.hoangviet.supportinglecturer.entity.response;

import com.otaliastudios.cameraview.filter.Filter;

public class ImageFilter {
    private int image;
    private boolean selected;
    private String name;
    private Class<? extends Filter> filterClass;

    public ImageFilter(int image, boolean selected, String name, Class<? extends Filter> filterClass) {
        this.image = image;
        this.selected = selected;
        this.name = name;
        this.filterClass = filterClass;
    }

    public Class<? extends Filter> getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(Class<? extends Filter> filterClass) {
        this.filterClass = filterClass;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
