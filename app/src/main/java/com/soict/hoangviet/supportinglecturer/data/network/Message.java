package com.soict.hoangviet.supportinglecturer.data.network;

import java.util.List;

public class Message{
	private List<String> name;

	public void setName(List<String> name){
		this.name = name;
	}

	public List<String> getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"Message{" + 
			"name = '" + name + '\'' + 
			"}";
		}
}