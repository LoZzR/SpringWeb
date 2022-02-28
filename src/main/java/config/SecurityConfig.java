package config;


import exceptions.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"services", "exceptions"})
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    AuthenticationEntryPoint restAuthenticationEntryPoint =
            (request, response, authException) ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Unauthorized");

    AccessDeniedHandler accessDeniedHandler = (request, response,
                                               accessDeniedException) -> {
        response.getOutputStream().print("You shall not pass!");
        response.setStatus(403);
    };

    SimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Autowired
    RestAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            auth.inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder)
                    .withUser("Oussama").password(passwordEncoder.encode("test123")).roles("USER")
                    .and().withUser("Kamel").password(passwordEncoder.encode("test123")).roles("USER", "ADMIN")
                    .and().withUser("Zakariae").password(passwordEncoder.encode("test123")).roles("ADMIN");
        } catch (Exception e) {
            throw new ConfigurationException("In-Memory authentication was not configured.", e);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .mvcMatchers("/persons/**").hasRole("ADMIN")
                .mvcMatchers("/**").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .httpBasic()
                .and()
                .logout();
    }
}
