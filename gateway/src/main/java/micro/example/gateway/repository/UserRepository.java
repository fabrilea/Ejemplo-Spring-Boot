package micro.example.gateway.repository;

import micro.example.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.authorities WHERE lower(u.username) = ?1")
    Optional<User> findOneWithAuthoritiesByUsernameIgnoreCase(String username);

    //Busca un usuario junto con sus autoridades
    //Recupera la entidad User y usa JOIN FETCH para cargar también sus relaciones de tipo authorities
    //(colección de roles o permisos asociados al usuario).
}

