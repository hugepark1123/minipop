/**
 *
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.users;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class UserDto {

	@Getter @Setter
	public static class Create {
		@NotBlank(message = "login Id cannot be empty or blank.")
		@Size(min = 5, max = 20, message = "loginId size must be between 4 ans 20")
		private String loginId;
		
		@NotBlank(message = "username cannot be empty or blank.")
		@Size(min = 4, max = 20, message = "username size must be between 4 ans 20")
		private String username;
		
		@NotBlank(message = "password must be between 5 and 20")
		@Size(min = 4, max = 20, message = "password size must be between 4 ans 20")
		private String password;
		
		private String shift;
	}
	
	@Getter @Setter
	public static class Reponse {
		private String loginId;
		
		private String username;
		
		private String shift;
	}
}
