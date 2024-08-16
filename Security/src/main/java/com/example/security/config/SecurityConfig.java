package com.example.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((req) -> req
                        .requestMatchers("/categories/**", "/products/**").authenticated() // Требует аутентификацию для /categories и /products
                        .anyRequest().permitAll() // Разрешает доступ ко всем остальным запросам
                )
                .formLogin((form) -> form
                        .loginPage("/login") // Укажите URL вашей страницы входа
                        .defaultSuccessUrl("/", true) // URL после успешного входа
                        .permitAll() // Разрешает доступ к странице входа
                )
                .logout((log) -> log
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll() // Разрешает доступ к выходу
                )
                .exceptionHandling((ex) -> ex
                        .accessDeniedPage("/access-denied") // Страница для заблокированных пользователей
                )
                .csrf().disable(); // Отключение CSRF защиты, если это необходимо
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
