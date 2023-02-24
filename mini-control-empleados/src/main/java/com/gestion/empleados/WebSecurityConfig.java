package com.gestion.empleados;

import com.gestion.empleados.entidades.Usuario;
import com.gestion.empleados.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.security.core.userdetails.User.withUsername;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UsuarioService usuarioService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(usuarioService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)  {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails usuario1 = withUsername("michael")
				.password("$2a$10$21k9QLX0vdSytZ36T56yHeDwn2y6DiHdJ2k7fCzccV7CuskmNQmEq")
				.roles("ADMIN")
				.build();

		UserDetails usuario3 = withUsername("jhoan")
				.password("$2a$10$21k9QLX0vdSytZ36T56yHeDwn2y6DiHdJ2k7fCzccV7CuskmNQmEq")
				.roles("USER")
				.build();
		
		UserDetails usuario2 = withUsername("admin")
				.password("$2a$10$21k9QLX0vdSytZ36T56yHeDwn2y6DiHdJ2k7fCzccV7CuskmNQmEq")
				.roles("ADMIN")	
				.build();

		List<Usuario> usuarioList = usuarioService.findAll();

		List<UserDetails> userDetails = new ArrayList<>();

		System.out.println("esto es userlists " + usuarioList);

		for (Usuario usuario: usuarioList) {
			UserDetails userDetails1 = withUsername(usuario.getNombre()).password(usuario.getPassword())
					.roles(usuario.getTipo()).build();
			userDetails.add(userDetails1);
		}

		System.out.println("esto es userdetails" + userDetails);


//		return new InMemoryUserDetailsManager(usuario1,usuario2,usuario3);
		return new InMemoryUserDetailsManager(userDetails);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		    .antMatchers("/").permitAll()
		    .antMatchers("/form/*","/eliminar/*").hasAuthority("ADMIN").and().
				authorizeRequests().antMatchers("/departamento").permitAll().
				antMatchers("/form/*","/eliminar/*").hasAuthority("ADMIN")
		    .anyRequest().authenticated()
		    .and()
		    .formLogin()
		        .loginPage("/login")
		        .permitAll()
		    .and()
		    .logout().permitAll();
	}
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//				.authorizeHttpRequests()
//				.mvcMatchers("/","/ws/**")
//				.permitAll()
//				.and()
//				.authorizeHttpRequests()
//				.anyRequest().authenticated()
//				.and()
//				.formLogin()
//				.and()
//				.logout( logout -> logout.logoutSuccessUrl("/"));
//		return http.build();
//	}



}
