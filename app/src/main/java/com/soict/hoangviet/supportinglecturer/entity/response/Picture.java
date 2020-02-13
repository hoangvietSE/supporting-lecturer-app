package com.soict.hoangviet.supportinglecturer.entity.response;

import com.google.gson.annotations.SerializedName;

public class Picture {

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Picture{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}