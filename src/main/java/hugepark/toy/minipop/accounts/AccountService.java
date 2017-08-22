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
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hugepark.toy.minipop.accounts.AccountDto.Request;

@Service
@Transactional
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository repository;
	
	public Optional<Account> createUser(Request.Create dto) {
		Account account = new Account();
		BeanUtils.copyProperties(dto, account);
		
		if(repository.findByLoginId(dto.getLoginId()).isPresent()) {
			throw new UserDuplicatedException("loginId [" + dto.getLoginId() + "] is duplicated");
		}

		// TODO password encoding
		
		return Optional.ofNullable(repository.save(account));
	}

	public List<Account> findAll() {
		return repository.findAll();
	}
	
	public Optional<Account> findOne(Long id) {
		return Optional.ofNullable(repository.findOne(id));
	}

	public Optional<Account> findByUsername(String username) {
		return repository.findByUsername(username);
	}

	public Page<Account> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> account = repository.findByUsername(username);
		
		if(account.isPresent()) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			
			authorities = account.get().getAuthorities()
					.stream()
					.map(authority -> 
						new SimpleGrantedAuthority(
								"ROLE_" + authority.getRole().name()))
					.collect(Collectors.toList());
			
			return new User(
					account.get().getUsername(), 
					account.get().getPassword(), 
					authorities);
		}
		
		throw new UsernameNotFoundException("User '" + username + "' not found.");
	}
}
