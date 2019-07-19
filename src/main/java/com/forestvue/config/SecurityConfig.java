package com.forestvue.config;


import com.forestvue.security.*;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.Filter;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = true)
@Log4j
public class SecurityConfig{




    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private JwtAuthenticationManager jwtAuthenticationManager;
        private String [] publicUrls = new String [] {
                "/api/**",
        };
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**").csrf().disable()
                    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        }
        Filter jwtAuthenticationFilter() {
            JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
            filter.setAuthenticationManager(jwtAuthenticationManager);
            // We do not need to do anything extra on REST authentication success, because there is no page to redirect to
            filter.setAuthenticationSuccessHandler((request, response, authentication) -> {});
            filter.setAuthenticationFailureHandler(new JwtFailureHandler());
            return filter;
        }

    }
    @Configuration
    @Order(2)
    public static class FormLoginSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Setter(onMethod_ = {@Autowired})
        private DataSource dataSource;

        @Autowired
        CustomUserDetailsService customUserDetailsService;

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/userpage").access("isAuthenticated()")
                    .antMatchers("/adminpage").access("isAuthenticated() && hasAnyRole('ROLE_ADMIN')")
                    .anyRequest().permitAll()
                    .and()
                    .formLogin()
                    .loginPage("/ajaxLogin")
                    .loginProcessingUrl("/login-processing")
                    .failureForwardUrl("/auth-failure")
                    .defaultSuccessUrl("/auth-success",false)
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .addLogoutHandler(new LogoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSION_ID");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService).
                    passwordEncoder(passwordEncoder());
            String queryUser = "select userid, userpw from tbl_member where username = ? ";
            String queryDetails = "select userid, auth from tbl_member_auth where userid = ? ";
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(passwordEncoder())
                    .usersByUsernameQuery(queryUser)
                    .authoritiesByUsernameQuery(queryDetails);
        }
    }




}
