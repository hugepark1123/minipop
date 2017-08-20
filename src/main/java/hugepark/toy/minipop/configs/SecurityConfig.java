/**
 *
 *
 * @author : hugepar1123
 * @date : 2017-08-19
 * @since : 
 */
package hugepark.toy.minipop.configs;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration
	protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {
		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth
			.inMemoryAuthentication()
			.withUser("admin")
				.password("admin")
				.roles("ADMIN", "USER", "ACTUATOR")
			.and()
			.withUser("user")
				.password("user")
				.roles("USER");
		}
	}
	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.authorizeRequests()
			.anyRequest()
				.fullyAuthenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error")
				.permitAll()
			.and()
				.logout()
				.permitAll();
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
			.inMemoryAuthentication()
				.withUser("admin")
					.password("admin")
					.roles("ADMIN", "USER")
				.and()
				.withUser("user")
					.password("user")
					.roles("USER");
		}
	}
}
