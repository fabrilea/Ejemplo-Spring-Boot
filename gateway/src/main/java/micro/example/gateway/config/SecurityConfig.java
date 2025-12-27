package micro.example.gateway.config;

import lombok.RequiredArgsConstructor;
import micro.example.gateway.repository.UserRepository;
import micro.example.gateway.security.jwt.JwtFilter;
import micro.example.gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //Declara que esta clase es una clase de configuración de Spring,
    // donde se definen los @Bean necesarios para configurar la seguridad.

    private final TokenProvider tokenProvider;
    @Lazy
    private final ReactiveUserDetailsService reactiveUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } //utiliza el algoritmo BCrypt para codificar contraseñas.

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Deshabilitar CSRF
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/authenticate").permitAll()
                        .pathMatchers("/api/usuarios").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterBefore(new JwtFilter(this.tokenProvider), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
    //Desactiva la protección CSRF (Cross-Site Request Forgery). En aplicaciones con JWT (JSON Web Tokens),
    // la protección CSRF no es tan relevante porque las solicitudes
    // se autentican mediante tokens en lugar de cookies de sesión.

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        // Implementación personalizada o autoconfiguración en el UserDetailsService existente.
        return username -> {
            // Ejemplo: Implementación mock, sustituir por lógica de repositorio.
            if ("admin".equals(username)) {
                return Mono.just(org.springframework.security.core.userdetails.User
                        .withUsername("admin")
                        .password(passwordEncoder().encode("password"))
                        .roles("ADMIN")
                        .build());
            } else {
                return Mono.empty();
            }
        };
    }
//Este metodo permite a Spring Security autenticar usuarios de forma reactiva en aplicaciones basadas en Spring WebFlux.


    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        return authentication -> Mono.justOrEmpty(userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(authentication.getName()))
                .switchIfEmpty(Mono.error(new BadCredentialsException("User not found")))
                .flatMap(user -> {
                    if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
                        return Mono.error(new BadCredentialsException("Invalid Credentials"));
                    }
                    return Mono.just(new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            user.getAuthorities().stream()
                                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                                    .toList()
                    ));
                });
    }

    //Resumen
    //
    //    Propósito:
    //        Configurar la seguridad reactiva en una aplicación WebFlux con autenticación basada en JWT.
    //
    //    Características:
    //        Uso de ReactiveAuthenticationManager para autenticar usuarios.
    //        Cadena de filtros que valida tokens JWT antes de permitir el acceso.
    //        Codificación de contraseñas con BCryptPasswordEncoder.
    //
    //    Flujo Típico:
    //        El cliente se autentica en /api/authenticate y recibe un JWT.
    //        Usa el JWT en solicitudes futuras como encabezado Authorization.
    //        El JwtFilter valida el token en cada solicitud y establece el contexto de seguridad.
    //
    //Este enfoque asegura una arquitectura escalable, compatible con aplicaciones no bloqueantes y basada en microservicios.

}
