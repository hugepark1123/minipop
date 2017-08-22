/**
 *
 *
 * @author : hugepar1123
 * @date : 2017-08-23
 * @since : 
 */
package hugepark.toy.minipop.accounts;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Authority {
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Account account;
	
	@Enumerated(EnumType.STRING)
	private Role role;

}
