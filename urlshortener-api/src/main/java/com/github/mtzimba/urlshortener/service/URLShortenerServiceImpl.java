package com.github.mtzimba.urlshortener.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.github.mtzimba.urlshortener.controller.ErrorEnum;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

@Service
public class URLShortenerServiceImpl implements URLShortenerService {

	private static final String URL = "URL";
	private static final String RANKING = "RANKING";
	
	// TODO MELHORAR - Estudar por num Repository
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public String shorten(String url, Optional<String> customAlias) {
		
		String alias = "";
		if (customAlias.isPresent()) {
			alias = customAlias.get();
			if (redisTemplate.opsForHash().hasKey(URL, alias)) {
				throw new IllegalArgumentException(ErrorEnum.CUSTOM_ALIAS_ALREADY_EXISTS.name());
			}
		} else {
			alias = Hashing.murmur3_32().hashString(url, Charsets.UTF_8).toString();
		}
		
		redisTemplate.opsForHash().put(URL, alias, url);
		return alias;
	}

	@Override
	public String retrieve(String alias) {
		if (!redisTemplate.opsForHash().hasKey(URL, alias)) {
			throw new IllegalArgumentException(ErrorEnum.SHORTENED_URL_NOT_FOUND.name());
		} 
		redisTemplate.opsForZSet().incrementScore(RANKING, alias, 1);
		return (String) redisTemplate.opsForHash().get(URL, alias);
	}

	@Override
	public List<String> getTopTenUrl() {
		List<String> topTenUrls = new ArrayList<>();
		Set<TypedTuple<String>> result = redisTemplate.opsForZSet().reverseRangeWithScores(RANKING, 0, 9);
		for (TypedTuple<String> t : result) {
			topTenUrls.add(t.getValue());
		}
		return topTenUrls;
	}
	
}
