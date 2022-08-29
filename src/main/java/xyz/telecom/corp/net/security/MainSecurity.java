package xyz.telecom.corp.net.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import xyz.telecom.corp.net.restController.CustomAuthProvider;
import xyz.telecom.corp.net.security.jwt.JwtEntryPoint;
import xyz.telecom.corp.net.security.jwt.JwtTokenFilter;
import xyz.telecom.corp.net.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    //@Autowired
    //private CustomAuthProvider customAuthProvider;
    
    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    	//auth.authenticationProvider(customAuthProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
    	/*
		http.cors().and().csrf().disable()
		    .authorizeRequests()
		    .antMatchers("/auth/**").permitAll()
		    .antMatchers("/user/**").hasRole("ADMIN")
		    .anyRequest().authenticated()
		    .and()
		    .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
		    .and()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        
		/*		
         --NO USE
         http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth/login","/auth/newUser").permitAll()
        .anyRequest().permitAll()
        .and()
        .httpBasic();
         --COMPLETE
        */ 
         
         http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		

    }
}
