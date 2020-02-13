package com.soict.hoangviet.supportinglecturer.entity.response;

import com.google.gson.annotations.SerializedName;

public class Data {

	@SerializedName("is_silhouette")
	private boolean isSilhouette;

	@SerializedName("width")
	private int width;

	@SerializedName("url")
	private String url;

	@SerializedName("height")
	private int height;

	public void setIsSilhouette(boolean isSilhouette){
		this.isSilhouette = isSilhouette;
	}

	public boolean isIsSilhouette(){
		return isSilhouette;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"is_silhouette = '" + isSilhouette + '\'' + 
			",width = '" + width + '\'' + 
			",url = '" + url + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}