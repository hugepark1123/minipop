/**
 * REF. https://spring.io/guides/tutorials/bookmarks/
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.security;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import hugepark.toy.minipop.accounts.AccountDto.Request;
import hugepark.toy.minipop.accounts.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityTest {
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private SecurityMockMvcRequestBuilders secMockMvc;
	
//	@Autowired
//	private UserController userController;
	
	@Autowired
	private AccountService accountService;
	
	
	@Test
	@WithMockUser(username="username", password="password", roles= {"USER"})
	public void getUsers() throws Exception {
		ResultActions result = this.mockMvc
		.perform(
				get("/api/users")
				.with(csrf())
				);
		result.andDo(print());
		result.andExpect(status().isNoContent());
	}
	
	@Test
	@WithMockUser(username="username", password="password", roles= {"USER"})
	public void formLogin() throws Exception {
	}
	
	
	@Test @Rollback
	@WithMockUser(username="username", password="password", roles={"ADMIN", "USER"})
	public void security() throws Exception {
		Request.Create create = new Request.Create();
		create.setLoginId("loginid");
		create.setUsername("username");
		create.setPassword("password");
		create.setEnabled(true);
		create.setEmail("email@abc.com");
		
//		Optional<Account> saved = accountService.createUser(create);
		
//		Authority auth1 = new Authority();
//		auth1.setAccount(saved.get());
//		auth1.setRole(Role.ADMIN);
//		
//		Authority auth2 = new Authority();
//		auth2.setAccount(saved.get());
//		auth2.setRole(Role.USER);
		
//		create.setAuthorities(Arrays.asList(auth1, auth2));
		
		ResultActions result = this.mockMvc
				.perform(
						post("/api/users")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper.writeValueAsString(create)));
			result.andDo(print());
			result.andExpect(status().isCreated());
			result.andExpect(jsonPath("$.username", is("username")));
	}
}
