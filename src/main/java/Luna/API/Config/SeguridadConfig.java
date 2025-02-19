package Luna.API.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // Habilitar CORS correctamente
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/postsPizarra").permitAll()
                .requestMatchers("/api/reportes").permitAll()
                .requestMatchers("/api/seguimiento").permitAll()
                .requestMatchers("/api/usuarios").permitAll()
                .requestMatchers("/api/usuarios/registro").permitAll() 
                .requestMatchers("/api/auth/login").permitAll()
                // Añadir roles con permisos para cada request CED ☺

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
}
