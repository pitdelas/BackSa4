package com.senai.back.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.senai.back.services.UsuarioService;

import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(
        AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder builder = http.getSharedObject(
            AuthenticationManagerBuilder.class
        );
        builder.userDetailsService(usuarioService).passwordEncoder(getPasswordEncoder());
        var authentication = builder.build();

        http.cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request ->
            request
                .requestMatchers("/login")
                .permitAll()
                .requestMatchers("/usuarios")
                .permitAll()
                .requestMatchers("/profissionais")
                .permitAll()
                .requestMatchers("/enderecos")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/vacinas")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/vacinas")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/vacinas")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/vacinas")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/aplicacoes")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/aplicacoes")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/aplicacoes")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/aplicacoes")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/unidadesDeSaude")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/unidadesDeSaude")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/unidadesDeSaude")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/unidadesDeSaude")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/carimbos")
                .permitAll()
                .requestMatchers(HttpMethod.PUT, "/carimbos")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/campanhas")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/campanhas")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/campanhas")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/campanhas")
                .hasRole("ADMIN")
                .anyRequest().authenticated()    
            )
            .authenticationManager(authentication)
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
