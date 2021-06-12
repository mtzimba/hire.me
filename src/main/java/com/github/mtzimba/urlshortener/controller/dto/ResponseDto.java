package com.github.mtzimba.urlshortener.controller.dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ResponseDto {

	private String alias;
	private String url;
	private Map<String, String> statistics = new HashMap<>();
	
	public ResponseDto(String alias, String url, StatisticsDto... statistics) {
		this.alias = alias;
		this.url = url;
		Arrays.asList(statistics).forEach(a -> this.statistics.put(a.getName(), a.getValue()));
	}

	public String getAlias() {
		return alias;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, String> getStatistics() {
		return statistics;
	}
	
}
