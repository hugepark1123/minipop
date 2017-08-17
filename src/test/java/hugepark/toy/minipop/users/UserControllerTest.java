/**
 * ref. https://spring.io/guides/tutorials/bookmarks/
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserController userController;
	
	@Test
	public void testUserController() {
		assertThat(userController).isNotNull();
	}
	
	@Test
	public void createUser() throws Exception {
		
		UserDto.Create create = new UserDto.Create();
		create.setLoginId("testid01");
		create.setUsername("홍길동");
		create.setPassword("1234");
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.username", is("홍길동")));
	}
	
	@Test
	public void createUser_Duplicated() throws Exception {
		
		UserDto.Create create = new UserDto.Create();
		create.setLoginId("testid01");
		create.setUsername("홍길동");
		create.setPassword("1234");
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.loginId", is("testid01")));
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)))
		.andDo(print())
		.andExpect(status().isConflict());
	}
	
	@Test
	public void getEmptyUsers() throws Exception {
		this.mockMvc
		.perform(get("/api/users"))
		.andDo(print())
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void getUsers() throws Exception {
		UserDto.Create create = new UserDto.Create();
		create.setLoginId("testid01");
		create.setUsername("홍길동");
		create.setPassword("1234");
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)));		
		this.mockMvc
		.perform(get("/api/users"))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void getUser_by_id() throws Exception {
		UserDto.Create create = new UserDto.Create();
		create.setLoginId("testid01");
		create.setUsername("홍길동");
		create.setPassword("1234");
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)));		
		this.mockMvc
		.perform(get("/api/users/{id}", 2L))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.username", is("홍길동")));
	}
	
	@Test
	public void getUser_notFound_by_id() throws Exception {
		UserDto.Create create = new UserDto.Create();
		create.setLoginId("testid01");
		create.setUsername("홍길동");
		create.setPassword("1234");
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)));		
		this.mockMvc
		.perform(get("/api/users/{id}", 100L))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getUser_by_letter_id() throws Exception {
		UserDto.Create create = new UserDto.Create();
		create.setLoginId("testid01");
		create.setUsername("홍길동");
		create.setPassword("1234");
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)));		
		this.mockMvc
		.perform(get("/api/users/{id}", "a"))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getUser_by_username() throws Exception {
		UserDto.Create create = new UserDto.Create();
		create.setLoginId("testid01");
		create.setUsername("홍길동");
		create.setPassword("1234");
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)));
		
		this.mockMvc
		.perform(get("/api/users")
				.param("username", "홍길동"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.username", is("홍길동")));
	}
	
	@Test
	public void getUser_notFound_by_username() throws Exception {
		UserDto.Create create = new UserDto.Create();
		create.setLoginId("testid01");
		create.setUsername("홍길동");
		create.setPassword("1234");
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)));
		
		this.mockMvc
		.perform(get("/api/users")
				.param("username", "hugepark"))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
}
