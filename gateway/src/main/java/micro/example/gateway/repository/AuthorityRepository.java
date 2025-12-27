package micro.example.gateway.repository;

import micro.example.gateway.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    //Este repositorio se utiliza para acceder y gestionar datos de la entidad Authority.
    //Es una pieza fundamental en sistemas que implementan seguridad basada en roles o permisos.
    //Usos comunes: Administración de roles y permisos en un sistema de autenticación/autorización.
}
