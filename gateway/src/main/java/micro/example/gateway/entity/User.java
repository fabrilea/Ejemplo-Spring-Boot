package micro.example.gateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    //La clase User implementa UserDetails, una interfaz de Spring Security
    // que describe los datos necesarios para la autenticación.

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( nullable = false )
    private String username;

    @Column( nullable = false )
    private String password;

    @JsonIgnore
    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    @JoinTable(
            name = "user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    private Set<Authority> authorities = new HashSet<>();

    public User( final String username ) {
        this.username = username.toLowerCase();
    }

    public void setAuthorities( final Collection<Authority> authorities ) {
        this.authorities = new HashSet<>( authorities );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convierte tus Authority en GrantedAuthority
        return authorities.stream()
                .map(authority -> (GrantedAuthority) authority::getName)
                .collect(Collectors.toSet());
    }

    //El metodo getAuthorities() proporciona los roles/permisos del usuario.
    // Spring Security los utiliza para determinar si el usuario tiene acceso a determinadas rutas o recursos.

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Ajusta si necesitas lógica específica
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Ajusta si necesitas lógica específica
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Ajusta si necesitas lógica específica
    }

    @Override
    public boolean isEnabled() {
        return true; // Puedes ajustar esto según la lógica de tu aplicación
    }


    //    Propósito:
    //        Representar un usuario en el sistema con sus datos y roles asociados.
    //        Integrarse con Spring Security para autenticación y autorización.
    //
    //    Relación con Authority:
    //        Define una relación muchos a muchos con la tabla de roles/permisos.
    //
    //    Compatibilidad con Spring Security:
    //        Implementa la interfaz UserDetails, proporcionando los métodos necesarios para la autenticación y autorización.
    //
    //    Ventaja:
    //        Es una implementación completa y extensible para manejar usuarios y roles en sistemas seguros.





}
