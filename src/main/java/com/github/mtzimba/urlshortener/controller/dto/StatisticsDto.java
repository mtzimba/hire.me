package com.github.mtzimba.urlshortener.controller.dto;

public class StatisticsDto {

	private String name;
	private String value;
	
	public StatisticsDto(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
