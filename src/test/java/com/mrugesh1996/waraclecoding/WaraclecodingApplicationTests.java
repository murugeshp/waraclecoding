package com.mrugesh1996.waraclecoding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrugesh1996.waraclecoding.entities.Cake;
import com.mrugesh1996.waraclecoding.repositry.CakeDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = WaraclecodingApplication.class)
@AutoConfigureMockMvc
@Transactional
class WaraclecodingApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	CakeDAO cakeDAO;

	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

	@BeforeEach
	public void setUpDatabase(){
		jdbc.execute("insert into cake(id, desc, image, title) values (100, 'Plum cake', 'murugesh favourite', 'http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg')");
	}

	@AfterEach
	public void setUpAfterTransaction(){
		jdbc.execute("delete from cake");
	}

	@Test
	public void getCakeHttpRequest() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(6)));
	}

	@Test
	public void getCakesJsonHttpRequest() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.get("/cakes"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/json"));
	}

	@Test
	public void createCakeHttpRequest() throws Exception {

		Cake cake = new Cake("Choclate cake", "Nice cake",
				"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg");

		mockMvc.perform(MockMvcRequestBuilders.post("/cakes/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cake)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(7)));
		Optional<Cake> cake1 = Optional.ofNullable(cakeDAO.findByTitle("Choclate cake"));
		assertNotNull(cake1.get(), "Cake should be found");
	}

	@Test
	public void getCakeInformationHttpRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cakeId", is(1)))
				.andExpect(jsonPath("$.title", is("Carrot cake")))
				.andExpect(jsonPath("$.desc", is("Bugs bunnys favourite")));
	}

	@Test
	public void getCakeInformationHttpRequestNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/{id}", 200))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status", is(404)))
				.andExpect(jsonPath("$.message", is("Cake not Found")));
	}
}
