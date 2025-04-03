package br.com.pesquisa_plus.pesquisa_plus.config;

import java.util.Locale;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.web.servlet.LocaleResolver;
// import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;


@Configuration
public class GlobalConfiguration {
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	// @Bean
	// public LocaleResolver localeResolver() {
	// 	return new FixedLocaleResolver(new Locale("pt", "BR"));
	// }

	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// 	return new BCryptPasswordEncoder();
	// }

	// @Bean
	// public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
	// 	UserDetails user = User.withUsername("admin").password(passwordEncoder.encode("123456")).roles("ADMIN")
	// 			.build();
	// 	return new InMemoryUserDetailsManager(user);
	// }
	
	// @Bean
	// public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	// return authenticationConfiguration.getAuthenticationManager();
	// }
	
	// @Bean()
	// public ElasticsearchTransport ElasticsearchTransportBuilder(RestClient RestClient) {
	// 	JacksonJsonpMapper mapper = new JacksonJsonpMapper();

	// 	mapper.objectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	// 	ElasticsearchTransport transport = new RestClientTransport(RestClient, mapper);

	// 	return transport;
	// }
	
}