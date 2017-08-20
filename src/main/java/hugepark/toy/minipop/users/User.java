/**
 *
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class User {
	
	@Id @GeneratedValue
	private Long Id;
	
	@NotBlank
	@Size(min = 5)
	@Column(unique = true)
	private String loginId;

	@NotBlank
	@Size(min = 3)
	private String username;
	
	@NotBlank
	@Size(min = 4)
	private String password;
	
	@Email
	private String email;
	
	private String shift;
	
	private int loginTrialCount;
	
	private boolean isAccountNonExpired;
	
	private boolean isAccountNonLocked;
	
	private boolean isCredentialsNonExpired;
	
	private boolean isEnabled;
}
