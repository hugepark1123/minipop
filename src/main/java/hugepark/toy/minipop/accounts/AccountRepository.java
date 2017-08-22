/**
 *
 *
 * @author : hugepar1123
 * @date : 2017-08-16
 * @since : 
 */
package hugepark.toy.minipop.accounts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findByUsername(String username);

	public Optional<Account> findByLoginId(String loginId);

}
