/**
 * REF. https://spring.io/guides/tutorials/bookmarks/
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.error;

import static org.hamcrest.CoreMatchers.is;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import hugepark.toy.minipop.users.UserDto.Request;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionHandlerTest {
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void createUser() throws Exception {
		
		Request.Create create = new Request.Create();
		create.setLoginId("testid01");
		create.setUsername("홍길동");
		create.setPassword("1234");
		
		this.mockMvc
		.perform(
				post("/api/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(create)))
		.andDo(print());
//		.andExpect(status().isCreated());
	}
}