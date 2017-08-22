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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Account {
	
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
	
	private boolean accountNonExpired;
	
	private boolean accountNonLocked;
	
	private boolean credentialsNonExpired;
	
	private boolean enabled;
	
	@OneToMany
	private List<Authority> authorities = new ArrayList<>();
}
