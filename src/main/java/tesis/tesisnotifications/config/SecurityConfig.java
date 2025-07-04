package tesis.tesisnotifications.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                // Permitir acceso a todos los endpoints SIN AUTENTICACIÓN
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                // Configuración para H2 Console
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                );

        return http.build();
    }
}