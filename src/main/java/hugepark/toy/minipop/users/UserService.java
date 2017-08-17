/**
 *
 *
 * @author : hugepark1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hugepark.toy.minipop.users.UserDto.Request;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public Optional<User> createUser(Request.Create dto) {
		User user = new User();
		BeanUtils.copyProperties(dto, user);
		
		if(repository.findByLoginId(dto.getLoginId()).isPresent()) {
			throw new UserDuplicatedException(dto.getLoginId());
		}

		// TODO password encoding
		
		return Optional.ofNullable(repository.save(user));
	}

	public List<User> findAll() {
		return repository.findAll();
	}
	
	public Optional<User> findOne(Long id) {
		return Optional.ofNullable(repository.findOne(id));
	}

	public Optional<User> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	public Page<User> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
