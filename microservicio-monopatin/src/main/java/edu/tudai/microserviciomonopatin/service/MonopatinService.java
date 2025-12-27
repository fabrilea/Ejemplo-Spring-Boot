package edu.tudai.microserviciomonopatin.service;

import edu.tudai.microserviciomonopatin.entity.Monopatin;
import edu.tudai.microserviciomonopatin.repository.MonopatinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MonopatinService {
    private final MonopatinRepository monopatinRepository;

    @Transactional(readOnly = true)
    public List<Monopatin> findAll() {
        return monopatinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Monopatin findById(Long id) {
        return monopatinRepository.findById(id).orElse(null);
    }

    @Transactional
    public Monopatin save(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    @Transactional
    public Monopatin update(Monopatin monopatin) {
        return monopatinRepository.save(monopatin);
    }

    @Transactional
    public void delete(Long id) {
        monopatinRepository.deleteById(id);
    }

    /*******************************************************/

    public Map<String, Long> obtenerEstadoMonopatines() {
        long enOperacion = monopatinRepository.countByDisponibleTrueAndEnMantenimientoFalse();
        long enMantenimiento = monopatinRepository.countByEnMantenimientoTrue();

        return Map.of("En Operación", enOperacion, "En Mantenimiento", enMantenimiento);
    }

    public List<Monopatin> obtenerMonopatinesCercanos(double latitud, double longitud, double radio) {
        return monopatinRepository.findMonopatinesCercanos(latitud, longitud, radio);
    }

    public List<Monopatin> obtenerMonopatinesKilometros(Double km, boolean pausa) {
        return monopatinRepository.findMonopatinesKilometros(km);
    }


    /**
     * Actualiza la disponibilidad de un monopatín.
     *
     * @param monopatinId ID del monopatín.
     * @param disponible  Nuevo estado de disponibilidad.
     */
    @Transactional
    public void actualizarDisponibilidad(Long monopatinId, boolean disponible) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new RuntimeException("Monopatín no encontrado con ID: " + monopatinId));
        monopatin.setDisponible(disponible);
        monopatinRepository.save(monopatin);
    }

    /**
     * Actualiza el estado de mantenimiento de un monopatín.
     *
     * @param monopatinId     ID del monopatín.
     * @param enMantenimiento Nuevo estado de mantenimiento.
     */
    @Transactional
    public void actualizarEnMantenimiento(Long monopatinId, boolean enMantenimiento) {
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new RuntimeException("Monopatín no encontrado con ID: " + monopatinId));
        monopatin.setEnMantenimiento(enMantenimiento);
        monopatinRepository.save(monopatin);
    }
}
