package com.github.mtzimba.urlshortener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class URLShortenerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shortenWithInvalidUrl() throws Exception {
		mockMvc.perform(put("/u/?url=invalidaurl")).andExpect(status().isBadRequest());
	}

	@Test
	public void shortenWithValidUrlAndWithCustonAlias() throws Exception {
		mockMvc.perform(put("/u/?url=http://www.opera.com/&CUSTOM_ALIAS=opera")).andExpect(status().isCreated());
	}

	@Test
	public void shortenWithValidUrlAndWithoutCustonAlias() throws Exception {
		mockMvc.perform(put("/u/?url=http://www.bemobi.com.br/")).andExpect(status().isCreated());
	}

	@Test
	public void shortenWithValidUrlAndWithExistingCustonAlias() throws Exception {
		mockMvc.perform(put("/u/?url=http://www.opera.com/&CUSTOM_ALIAS=opera")).andExpect(status().isBadRequest());
	}

	public void retriveNotFound() throws Exception {
		mockMvc.perform(get("/u/naotem")).andExpect(status().isNotFound());
	}

	@Test
	public void retrieveValid() throws Exception {
		mockMvc.perform(get("/u/opera")).andExpect(status().is3xxRedirection());
	}

	@Test
	public void listTopTen() throws Exception {
		mockMvc.perform(get("/u/")).andExpect(status().isOk());
	}

}
