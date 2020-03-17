package com.soict.hoangviet.supportinglecturer.entity.response.youtube;

import com.google.gson.annotations.SerializedName;

public class High{

	@SerializedName("url")
	private String url;

	@SerializedName("width")
	private int width;

	@SerializedName("height")
	private int height;

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
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
			"High{" + 
			"url = '" + url + '\'' + 
			",width = '" + width + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}