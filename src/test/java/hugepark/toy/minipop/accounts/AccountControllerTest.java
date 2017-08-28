/**
 * REF. https://spring.io/guides/tutorials/bookmarks/
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.accounts;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import hugepark.toy.minipop.accounts.AccountDto.Request;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerTest {
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;
	
//	@Autowired
//	private UserController userController;
	
	@Autowired
	private AccountService accountService;
	
//	@Test @Rollback
//	public void testUserController() {
//		assertThat(userController).isNotNull();
//	}
	
	@Test @Rollback
	public void createUser() throws Exception {
		// invalid user create
		Request.Create create = new Request.Create();
		create.setLoginId("testid01");
		create.setUsername("홍");
		create.setPassword("1234");
		
		ResultActions result = this.mockMvc
			.perform(
					post("/api/users")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(objectMapper.writeValueAsString(create)));
		result.andDo(print());
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.fieldErrors[0].field", is("username")));
		
		// valid user create
		create.setUsername("홍길동");
		
		result = this.mockMvc
				.perform(
						post("/api/users")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper.writeValueAsString(create)));
		result.andDo(print());
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.username", is("홍길동")));
		
		// duplicated user create
		result = this.mockMvc
				.perform(
						post("/api/users")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper.writeValueAsString(create)));
		result.andDo(print());
		result.andExpect(status().isConflict());
		result.andExpect(jsonPath("$.code", is("409")));
	}
	
	@Test @Rollback
	public void getUsers() throws Exception {
		// get all user but empty
		ResultActions result = this.mockMvc.perform(get("/api/users"));
		result.andDo(print());
		result.andExpect(status().isNoContent());
		
		// get all user and OK
		Request.Create create = new Request.Create();
		create.setLoginId("testuser01");
		create.setUsername("testusername01");
		create.setPassword("password01");
		
		accountService.createUser(create);

		result = this.mockMvc.perform(get("/api/users"));
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].username", is("testusername01")));
	}
	
	@Test @Rollback
	public void getUser_by_id() throws Exception {
		// get ONE valid user but not found
		ResultActions result = this.mockMvc.perform(get("/api/users/{id}", 200L));
		result.andDo(print());
		result.andExpect(status().isNotFound());
		
		// get ONE invalid user but bad request
		result = this.mockMvc.perform(get("/api/users/{id}", "StringValueNotNumber"));
		result.andDo(print());
		result.andExpect(status().isBadRequest());
		
		// get ONE valid user and OK
		Request.Create create = new Request.Create();
		create.setLoginId("testuser01");
		create.setUsername("testusername01");
		create.setPassword("password01");
		
		Optional<Account> user = accountService.createUser(create);
		
		result = this.mockMvc.perform(get("/api/users/{id}", user.get().getId()));
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.username",is("testusername01")));
	}
	
	@Test @Rollback
	public void getUser_by_username() throws Exception {
		// get ONE valid user but not found
		ResultActions result = this.mockMvc.perform(
				get("/api/users")
				.param("username", "someone"));
		result.andDo(print());
		result.andExpect(status().isNotFound());
		
		// get ONE invalid user but bad request
		// TODO use REGEX for valid parameter and take bad request
		
		// get ONE valid user and OK
		Request.Create create = new Request.Create();
		create.setLoginId("testuser01");
		create.setUsername("testusername01");
		create.setPassword("password01");
		
		accountService.createUser(create);

		result = this.mockMvc.perform(
				get("/api/users")
					.param("username", "testusername01"));
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.username", is("testusername01")));
	}
}
