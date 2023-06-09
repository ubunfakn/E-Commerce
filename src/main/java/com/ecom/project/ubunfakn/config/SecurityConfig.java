package com.ecom.project.ubunfakn.config;


import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {
    
    
    /****************************Function********************************** */
    @Bean
    public UserDetailsService userDetailsService()
    {
        return new UserDetailService();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity.authorizeHttpRequests()
        .requestMatchers("/user/admin/**").hasRole("ADMIN")
        .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
        .requestMatchers("/**").permitAll()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/doprocess")
        .defaultSuccessUrl("/user/")
        .and().csrf().disable().build();

    }



    @Bean
    public static PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return daoAuthenticationProvider;
    }

    
}
