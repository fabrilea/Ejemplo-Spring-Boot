package edu.tudai.microservicioadministrador.service;

import edu.tudai.microservicioadministrador.client.MonopatinClient;
import edu.tudai.microservicioadministrador.client.ViajeClient;
import edu.tudai.microservicioadministrador.dto.ReporteKilometrosDTO;
import edu.tudai.microservicioadministrador.dto.ViajeDTO;
import edu.tudai.microservicioadministrador.entity.Mantenimiento;
import edu.tudai.microservicioadministrador.repository.MantenimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final MonopatinClient monopatinClient;
    private final ViajeClient viajeClient;

    @Transactional(readOnly = true)
    public List<Mantenimiento> findAll() {
        return mantenimientoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mantenimiento findById(Long id) {
        return mantenimientoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Mantenimiento save(Mantenimiento mantenimiento) {
        return mantenimientoRepository.save(mantenimiento);
    }

    @Transactional
    public void delete(Long id) {
        mantenimientoRepository.deleteById(id);
    }

    @Transactional
    public Mantenimiento update(Mantenimiento mantenimiento) {
        return mantenimientoRepository.save(mantenimiento);
    }

    /****************************************************/

    @Transactional
    public Mantenimiento iniciarMantenimiento(Long monopatinId, String descripcion) {
        // Verificar si el monopatín ya está en mantenimiento
        if (mantenimientoRepository.findByMonopatin(monopatinId)) {
            throw new RuntimeException("El monopatín ya está en mantenimiento");
        }

        // Crear el registro de mantenimiento
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setMonopatinId(monopatinId);
        mantenimiento.setFechaInicio(LocalDateTime.now());
        mantenimiento.setDescripcion(descripcion);
        mantenimientoRepository.save(mantenimiento);

        // Marcar el monopatín como no disponible
        monopatinClient.actualizarDisponibilidad(monopatinId, false);
        monopatinClient.actualizarEnMantenimiento(monopatinId, true);

        return mantenimiento;
    }

    @Transactional
    public Mantenimiento finalizarMantenimiento(Long mantenimientoId) {

        Mantenimiento mantenimiento = mantenimientoRepository.findById(mantenimientoId).orElse(null);

        // Establecer la fecha de finalización
        assert mantenimiento != null;
        mantenimiento.setFechaFin(LocalDateTime.now());
        mantenimientoRepository.save(mantenimiento);

        // Marcar el monopatín como disponible
        monopatinClient.actualizarDisponibilidad(mantenimiento.getMonopatinId(), true);
        monopatinClient.actualizarEnMantenimiento(mantenimiento.getMonopatinId(), false);

        return mantenimiento;
    }

    public List<ReporteKilometrosDTO> generarReporteUsoMonopatines(boolean incluirPausas) {
        List<ViajeDTO> viajes = viajeClient.getAllViajes();

        // Agrupar por MonopatinId y consolidar datos
        Map<Long, ReporteKilometrosDTO> reporteMap = viajes.stream()
                .map(v -> {
                    double tiempoTotal = v.getTiempoUso();

                    if (!incluirPausas && v.getPausas() != null) {
                        double tiempoPausas = v.getPausas().stream()
                                .filter(p -> p.getFin() != null) // Ignora pausas en curso
                                .mapToDouble(p -> Duration.between(p.getInicio(), p.getFin()).toMinutes())
                                .sum();
                        tiempoTotal -= tiempoPausas;
                    }

                    return new ReporteKilometrosDTO(
                            v.getMonopatinId(),
                            v.getKilometrosRecorridos(),
                            tiempoTotal
                    );
                })
                .filter(dto -> dto.getKilometrosRecorridos() > 100) // Filtrar solo los relevantes
                .collect(Collectors.toMap(
                        ReporteKilometrosDTO::getMonopatinId, // Usar MonopatinId como clave
                        dto -> dto, // Usar el DTO como valor
                        (dto1, dto2) -> new ReporteKilometrosDTO(
                                dto1.getMonopatinId(),
                                dto1.getKilometrosRecorridos() + dto2.getKilometrosRecorridos(), // Sumar kilómetros
                                dto1.gettiempoTotal() + dto2.gettiempoTotal() // Sumar tiempo
                        )
                ));

        // Convertir el Map a una lista
        return new ArrayList<>(reporteMap.values());
    }


    //Propósito:
    //    Generar un reporte de uso de monopatines con información sobre kilómetros recorridos y tiempo de uso, ajustado según las pausas si se especifica.
    //
    //Flujo:
    //    Obtiene todos los viajes de un cliente.
    //    Calcula el tiempo total de uso por viaje, excluyendo pausas si es necesario.
    //    Transforma los datos en objetos ReporteKilometrosDTO.
    //
    //Consideraciones:
    //    Maneja viajes sin pausas (v.getPausas() == null).
    //    Ignora pausas en curso (p.getFin() == null).
    //    Ajusta correctamente el tiempo según la inclusión o exclusión de pausas.
    //
    //Salida:
    //    Una lista de ReporteKilometrosDTO con datos clave sobre el uso de los monopatines.
}
