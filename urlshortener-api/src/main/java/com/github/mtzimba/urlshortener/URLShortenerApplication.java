package com.github.mtzimba.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class URLShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(URLShortenerApplication.class, args);
	}
	
}
