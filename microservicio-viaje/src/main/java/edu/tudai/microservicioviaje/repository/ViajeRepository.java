package edu.tudai.microservicioviaje.repository;

import edu.tudai.microservicioviaje.dto.RespuestaDTO;
import edu.tudai.microservicioviaje.entity.Viaje;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    List<Viaje> findByMonopatinId(Long monopatinId);

    @Query("SELECT new edu.tudai.microservicioviaje.dto.RespuestaDTO(v.monopatinId, COUNT(v.id)) " +
            "FROM Viaje v " +
            "WHERE YEAR(v.fechaInicio) = :anio " +
            "GROUP BY v.monopatinId " +
            "HAVING COUNT(v.id) > :minViajes")
    List<RespuestaDTO> findByViajesPorAnio(@Param("minViajes") int minViajes, @Param("anio") int anio);



}
