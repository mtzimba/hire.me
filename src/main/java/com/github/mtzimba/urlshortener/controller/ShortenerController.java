package com.github.mtzimba.urlshortener.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.mtzimba.urlshortener.controller.dto.ResponseDto;
import com.github.mtzimba.urlshortener.controller.dto.ResponseErrorDto;
import com.github.mtzimba.urlshortener.controller.dto.StatisticsDto;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * @author Matheus Cardoso
 */
@RestController
@RequestMapping("/")
public class ShortenerController {

	final static Logger LOG = LoggerFactory.getLogger(ShortenerController.class);
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@PutMapping("create")
	public ResponseEntity<?> shorten(@RequestParam(value = "url") String url,
			@RequestParam(value = "CUSTOM_ALIAS", required = false) Optional<String> customAlias,
			UriComponentsBuilder uriBuilder) {

		LocalDateTime start = LocalDateTime.now();
		
		// Criar hash se não existir
		String alias = "";
		if (customAlias.isPresent()) {
			alias = customAlias.get();
			// Validar se já existe
			if (redisTemplate.hasKey(alias)) {
				return ResponseEntity.badRequest().body(new ResponseErrorDto(alias, ErroEnum.CUSTOM_ALIAS_ALREADY_EXISTS));
			}
		} else {
			alias = Hashing.murmur3_32().hashString(url, Charsets.UTF_8).toString();
		}
		LOG.info(alias);
		
		// Salvar na base
		redisTemplate.opsForValue().set(alias, url);

		URI uri = uriBuilder.path("/u/{alias}").buildAndExpand(alias).toUri();
		return ResponseEntity.created(uri).body(new ResponseDto(alias, uri.toString(), 
				new StatisticsDto("time_taken", ChronoUnit.MILLIS.between(start, LocalDateTime.now()) + "ms")));
	}

	@GetMapping("u/{alias}")
	public ResponseEntity<?> retrieve(@PathVariable("alias") String alias) {
		
		if (!redisTemplate.hasKey(alias)) {
			return ResponseEntity.notFound().build();
		} else 
		
		redisTemplate.opsForValue().increment("count_"+alias);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redisTemplate.opsForValue().get(alias))).build();
	}
	
	@GetMapping("u/")
	public ResponseEntity<?> topTen() {
		return ResponseEntity.ok().body(redisTemplate.hasKey("count_*"));
	}
	
}
