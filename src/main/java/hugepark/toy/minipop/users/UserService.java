/**
 *
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.users;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public Optional<User> createUser(UserDto.Create dto) {
		User user = new User();
		BeanUtils.copyProperties(dto, user);
		
		// TODO password encoding
		
		return Optional.ofNullable(repository.save(user));
	}
}
