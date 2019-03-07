package com.example.monitor.configuration;

import de.codecentric.boot.admin.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(AdminServerProperties.class)
public  class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private AdminServerProperties adminServerProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Page with login form is served as /login.html and does a POST on /login
        http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
        // The UI does a POST on /logout on logout
        http.logout().logoutUrl(adminServerProperties.getContextPath()+"/logout");
        // The ui currently doesn't support csrf
        http.csrf().disable();

//        // Requests for the login page and the static assets are allowed
        http.authorizeRequests()
                .antMatchers("/login.html","/**/*.css", "/img/**", "/third-party/**")
                .permitAll();

        // ... and any other request needs to be authorized
        http.authorizeRequests().antMatchers(adminServerProperties.getContextPath()+"/**").authenticated();
       ;
        // Enable so that the clients can authenticate via HTTP basic for registering
        http.httpBasic();
    }
}