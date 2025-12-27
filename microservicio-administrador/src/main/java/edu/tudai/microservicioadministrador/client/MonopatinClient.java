package edu.tudai.microservicioadministrador.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8002/api/monopatin")
public interface MonopatinClient {

    //Este código define un cliente Feign que interactúa con el
    // microservicio microservicio-monopatin, encargado de gestionar monopatines.
    // El cliente realiza solicitudes HTTP para actualizar y consultar el estado de los monopatines.
    // Uso del Cliente Feign: El cliente MonopatinClient puede ser inyectado y utilizado en un servicio o controlador
    // Ventajas del uso de Feign: La interfaz define directamente los endpoints y sus parámetros.
    // Feign genera automáticamente la implementación.

    @PutMapping("/{id}/disponibilidad")
    void actualizarDisponibilidad(@PathVariable("id") Long id, @RequestParam("disponible") boolean disponible);

    @PutMapping("/{id}/mantenimiento")
    void actualizarEnMantenimiento(@PathVariable("id") Long id, @RequestParam("mantenimiento") boolean mantenimiento);

    @GetMapping("/estado")
    Map<String, Long> obtenerEstadoMonopatines();
}
