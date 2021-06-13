package com.github.mtzimba.urlshortener.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.github.mtzimba.urlshortener.service.URLShortenerService;

/**
 * @author Matheus Cardoso
 */
@RestController
@RequestMapping("/u")
@CrossOrigin(origins = "*")
public class URLShortenerController {

	final static Logger LOG = LoggerFactory.getLogger(URLShortenerController.class);
	
	@Autowired
	private URLShortenerService shortenerService;
	
	@PutMapping("/")
	public ResponseEntity<?> shorten(@RequestParam(value = "url") String url,
			@RequestParam(value = "CUSTOM_ALIAS", required = false) Optional<String> customAlias,
			UriComponentsBuilder uriBuilder) {

		LocalDateTime start = LocalDateTime.now();
		
		// Verificar se a URL é válida
		if (!UrlValidator.getInstance().isValid(url)) {
			return ResponseEntity.badRequest().body(new ResponseErrorDto(url, ErrorEnum.INVALID_URL));
		}
		
		try {
			String alias = shortenerService.shorten(url, customAlias);
			URI uri = uriBuilder.path("/u/{alias}").buildAndExpand(alias).toUri();
			return ResponseEntity.created(uri).body(new ResponseDto(alias, uri.toString(), 
					new StatisticsDto("time_taken", ChronoUnit.MILLIS.between(start, LocalDateTime.now()) + "ms")));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new ResponseErrorDto(customAlias.get(), ErrorEnum.CUSTOM_ALIAS_ALREADY_EXISTS));
		}
	}

	@GetMapping("/{alias}")
	public ResponseEntity<?> retrieve(@PathVariable("alias") String alias) {
		try {
			String url = shortenerService.retrieve(alias);
			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getTopTenUrl(UriComponentsBuilder uriBuilder) {
		String baseURI = uriBuilder.path("/u/").build().toString();
		
		List<URI> urls = new ArrayList<>();
		List<String> topTenUrl = shortenerService.getTopTenUrl();
		for (String alias : topTenUrl) {
			urls.add(URI.create(baseURI + alias));
		}
		return ResponseEntity.ok().body(urls);
	}
	
}
