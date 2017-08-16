/**
 *
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class User {
	
	@Id @GeneratedValue
	private Long Id;
	
	@NotBlank
	@Size(min = 5)
	private String loginId;

	@NotBlank
	@Size(min = 3)
	private String username;
	
	@NotEmpty
	@Size(min = 4)
	private String password;
	
	private String shift;
}
