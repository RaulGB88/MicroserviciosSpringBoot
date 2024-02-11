package inicio;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public InMemoryUserDetailsManager detailsManager() throws Exception{
		List<UserDetails> users=List.of(
				User.withUsername("user1")
				.password("{noop}user1")
				.authorities("USERS")
				.build(),
				User.withUsername("admin")
				.password("{noop}admin")
				.authorities("USERS","ADMIN")
				.build()
				 

				);
		return new InMemoryUserDetailsManager(users);
	}
	/*public DataSource dataSource() {
	DriverManagerDataSource ds=new DriverManagerDataSource();
	ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
	ds.setUrl("jdbc:mysql://localhost:3307/springsecurity?serverTimezone=UTC");
	ds.setUsername("root");
	ds.setPassword("root");
	return ds;
	}*/
	
	
	/*la siguiente configuración será para el caso de 
	 usuarios en una base de datos
	@Bean
	public JdbcUserDetailsManager usersDetailsJdbc() {
		JdbcUserDetailsManager jdbcDetails=new JdbcUserDetailsManager(datasource());
		jdbcDetails.setUsersByUsernameQuery("select username, password, enabled"
	       	+ " from users where username=?");
		jdbcDetails.setAuthoritiesByUsernameQuery("select username, authority "
	       	+ "from authorities where username=?");
		return jdbcDetails;
	}*/
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/contactos").hasAnyRole("ADMIN")
		.antMatchers("/contactos").authenticated()
		.and()
		.httpBasic();
		return http.build();
	}
}
