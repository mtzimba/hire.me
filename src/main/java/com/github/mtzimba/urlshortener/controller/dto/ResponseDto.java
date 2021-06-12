package com.github.mtzimba.urlshortener.controller.dto;

import java.util.Arrays;
import java.util.List;

public class ResponseDto {

	private String alias;
	private String url;
	private List<StatisticsDto> statistics;
	
	public ResponseDto(String alias, String url, StatisticsDto... statistics) {
		this.alias = alias;
		this.url = url;
		this.statistics = Arrays.asList(statistics);
	}

	public String getAlias() {
		return alias;
	}

	public String getUrl() {
		return url;
	}

	public List<StatisticsDto> getStatistics() {
		return statistics;
	}
	
}
