package edu.tudai.microservicioadministrador.client;


import edu.tudai.microservicioadministrador.dto.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-usuario", url = "http://localhost:8001/api/cuenta")
public interface cuentaClient {

    @PutMapping("/anularCuenta/{id}")
    void anularCuenta(@PathVariable("id") long id);

    @GetMapping("/viajes/{minViajes}/{anio}")
    List<MonopatinDTO> obtenerMonopatinesConMasViajes(@PathVariable("minViajes") int minViajes, @PathVariable("anio") int anio);

    //Propósito:
    //    Este cliente Feign se utiliza para interactuar con el microservicio microservicio-usuario.
    //
    //Endpoints:
    //    anularCuenta: Envía una solicitud PUT para anular una cuenta.
    //    obtenerMonopatinesConMasViajes: Envía una solicitud GET para obtener monopatines con más viajes en un año específico.
    //
    //Ventajas de Feign:
    //    Abstrae la lógica HTTP.
    //    Fácil de integrar y usar en aplicaciones Spring Boot.
    //
    //Aplicaciones Reales:
    //    Ideal para arquitecturas de microservicios donde los servicios deben comunicarse entre sí.

}
