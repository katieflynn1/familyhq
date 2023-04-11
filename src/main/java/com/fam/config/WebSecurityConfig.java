package com.fam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fam.service.CountryService;
import com.fam.service.CountryServiceImpl;
import com.fam.service.UserServiceImpl;
import com.fam.service.VariantService;
import com.fam.service.VariantServiceImpl;
import com.fam.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
public class WebSecurityConfig {

    @Autowired
    private CustomLoginSucessHandler sucessHandler;

    @Autowired
    UserRepository userRepository;
    
    @Bean
    public CountryService countryService() {
    	return new CountryServiceImpl(null);
    }

    @Bean
    public VariantService variantService() {
    	return new VariantServiceImpl(null);
    }
    
    @Bean
    public VariantServiceImpl variantServiceImpl() {
		return new VariantServiceImpl(null);
    	
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

                httpSecurity.authorizeHttpRequests()
                // URL matching for accessibility
                .requestMatchers("/", "/login", "/register").permitAll()
                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/account/**").hasAnyAuthority("USER")
                .anyRequest().authenticated()
                .and()
                // form login
                .csrf().disable().formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .successHandler(sucessHandler)
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                // logout
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");

                httpSecurity.authenticationProvider(authenticationProvider());
                httpSecurity.headers().frameOptions().sameOrigin();

                return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }

}