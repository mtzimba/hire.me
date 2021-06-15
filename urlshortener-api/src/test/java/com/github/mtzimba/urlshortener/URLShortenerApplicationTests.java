package com.github.mtzimba.urlshortener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class URLShortenerApplicationTests {

	// TODO MELHORAR - Estudar por num Repository
	private static final String URL = "URL";
	private static final String RANKING = "RANKING";
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() throws Exception {
		redisTemplate.delete(Arrays.asList(URL, RANKING));
		
		// Para validar já existente
		// Inicia com uma URL
		redisTemplate.opsForHash().put(URL, "bemobi", "http://www.bemobi.com.br");
	}
	
	@Test
	public void shortenWithInvalidUrl() throws Exception {
		mockMvc.perform(put("/u/?url=invalidaurl")).andExpect(status().isBadRequest());
	}

	@Test
	public void shortenWithValidUrlAndWithCustonAlias() throws Exception {
		mockMvc.perform(put("/u/?url=http://www.google.com/&CUSTOM_ALIAS=google")).andExpect(status().isCreated());
	}

	@Test
	public void shortenWithValidUrlAndWithoutCustonAlias() throws Exception {
		mockMvc.perform(put("/u/?url=http://www.bemobi.com.br/")).andExpect(status().isCreated());
	}

	@Test
	public void shortenWithValidUrlAndWithExistingCustonAlias() throws Exception {
		mockMvc.perform(put("/u/?url=http://www.bemobi.com.br&CUSTOM_ALIAS=bemobi")).andExpect(status().isBadRequest());
	}

	public void retriveNotFound() throws Exception {
		mockMvc.perform(get("/u/naotem")).andExpect(status().isNotFound());
	}

	@Test
	public void retrieveValid() throws Exception {
		mockMvc.perform(get("/u/bemobi")).andExpect(status().is3xxRedirection());
	}

	@Test
	public void listTopTen() throws Exception {
		// TODO Melhorar. Tratar o conteúdo
		mockMvc.perform(get("/u/")).andExpect(status().isOk());
	}

}
