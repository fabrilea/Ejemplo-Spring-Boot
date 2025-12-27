package edu.tudai.microserviciofactura.service;

import edu.tudai.microserviciofactura.entity.DetalleFactura;
import edu.tudai.microserviciofactura.entity.Factura;
import edu.tudai.microserviciofactura.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;

    @Transactional(readOnly = true)
    public List<Factura> findAll() {
        for (Factura factura : facturaRepository.findAll()) {
            if (!factura.getDetallesFactura().isEmpty()) {
                factura.calcularMontoTotal();
                facturaRepository.save(factura);
            }
        }
        return facturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Factura findById(Long id) {
        Factura factura = facturaRepository.findById(id).orElse(null);
        assert factura != null;
        factura.calcularMontoTotal();
        facturaRepository.save(factura);
        return facturaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Transactional
    public Factura update(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Transactional
    public void delete(Long id) {
        facturaRepository.deleteById(id);
    }

    /************************************************************/

    public double obtenerTotalFacturado(int anio, int mesInicio, int mesFin) {
        for (Factura factura : facturaRepository.findAll()) {
            factura.calcularMontoTotal();
            facturaRepository.save(factura);
        }
        return facturaRepository.obtenerTotalFacturado(anio, mesInicio, mesFin);
    }
}
