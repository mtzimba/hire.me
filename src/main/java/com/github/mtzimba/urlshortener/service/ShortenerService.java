package com.github.mtzimba.urlshortener.service;

import java.util.List;
import java.util.Optional;

public interface ShortenerService {

	
	public String shorten(String url, Optional<String> customAlias);
	
	public String retrieve(String alias);
	
	public List<String> getTopTenUrl();
}
