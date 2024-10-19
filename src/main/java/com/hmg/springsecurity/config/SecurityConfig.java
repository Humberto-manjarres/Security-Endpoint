package com.hmg.springsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity /*TODO: esta anotación habilita la seguridad a nivel de métodos dentro de nuestra aplicación*/
@RequiredArgsConstructor
@EnableMethodSecurity /*TODO: esta anotación habilita la seguridad a los métodos */
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(publicEndPoints()).permitAll() /*TODO: en el método requestMatchers() registra los endPoint que serán permitidos */
                        .anyRequest().authenticated()) /*TODO:  los demás endPoint deben estar autenticados */
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider) /*TODO: proveedor de autenticación*/
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); /*TODO: filtro que se ejecutará antes del proceso de autenticación*/
        return httpSecurity.build();
    }

    private RequestMatcher publicEndPoints(){
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/greeting/sayHelloPublic"),
                new AntPathRequestMatcher("/api/auth/**")
        );
    }
}
