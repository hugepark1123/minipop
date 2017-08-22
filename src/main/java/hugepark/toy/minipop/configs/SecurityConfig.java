/**
 *
 *
 * @author : hugepar1123
 * @date : 2017-08-19
 * @since : 
 */
package hugepark.toy.minipop.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import hugepark.toy.minipop.accounts.AccountService;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class WebSecurity extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private AccountService accountService;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.and()
			.httpBasic();
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(accountService);
		}
	}
	
//	@Order(Ordered.HIGHEST_PRECEDENCE)
//	@Configuration
//	protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {
//		@Override
//		public void init(AuthenticationManagerBuilder auth) throws Exception {
//			auth
//			.inMemoryAuthentication()
//			.withUser("admin")
//				.password("admin")
//				.roles("ADMIN", "USER", "ACTUATOR")
//			.and()
//			.withUser("user")
//				.password("user")
//				.roles("USER");
//		}
//	}
}
