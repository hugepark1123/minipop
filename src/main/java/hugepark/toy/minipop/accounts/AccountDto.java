/**
 *
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.accounts;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class AccountDto {

	public static class Request {

		@Getter @Setter
		public static class Create {
			@NotBlank
			@Size(min = 5, max = 20)
			private String loginId;
			
			@NotBlank
			@Size(min = 2, max = 20)
			private String username;
			
			@NotBlank
			@Size(min = 4, max = 20)
			private String password;
			
			private String shift;
			
			private String email;
			
			private int loginTrialCount;
			
			private boolean accountNonExpired;
			
			private boolean accountNonLocked;
			
			private boolean credentialsNonExpired;
			
			private boolean enabled;
			
			private List<Authority> authorities = new ArrayList<>();
			
		}
		
		@Getter @Setter
		public static class Update {
			
		}
		
		@Getter @Setter
		public static class Delete {
			
		}
	}
	
	@Getter @Setter @ToString
	public static class Reponse {
		private Long id;
		
		private String loginId;
		
		private String username;
		
		private String shift;
	}
}
