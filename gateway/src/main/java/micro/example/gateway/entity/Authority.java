package micro.example.gateway.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Authority implements GrantedAuthority {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return this.name; // Spring Security usará este valor como el rol o permiso
    }

    //Este código define una entidad JPA llamada Authority que representa una autoridad
    // (como un rol o permiso) en el sistema.
    // Esta entidad está integrada con Spring Security y se utiliza en el contexto de autenticación y autorización.

    //La entidad Authority está relacionada con la clase de usuario
    // (por ejemplo, una entidad User) en una relación muchos a muchos.
    // Esto se utiliza para asociar usuarios con roles o permisos en un sistema de seguridad.

    //Cuando un usuario inicia sesión, Spring Security recupera las autoridades
    // asociadas a ese usuario y las utiliza para determinar qué acciones puede realizar.


}

