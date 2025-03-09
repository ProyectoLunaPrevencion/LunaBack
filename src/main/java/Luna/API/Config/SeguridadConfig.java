package Luna.API.Config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    private final JwtUtil jwtUtil;

    public SeguridadConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios/registro").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/postsPizarra").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/usuarios/current").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/reportes").hasRole("ESTUDIANTE")            
                .requestMatchers("/**").hasRole("ORIENTACION") 
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFiltroAutenticacion(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptions -> 
                exceptions
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                    .accessDeniedHandler(jwtAccessDeniedHandler())
            );

        return http.build();
    }

    @Bean
    public JwtFiltroAutenticacion jwtFiltroAutenticacion() {
        return new JwtFiltroAutenticacion(jwtUtil);
    }

    @Bean
    public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return (request, response, authException) -> 
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
    }

    @Bean
    public AccessDeniedHandler jwtAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> 
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
    }
}