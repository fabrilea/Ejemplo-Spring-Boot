package edu.tudai.microservicioadministrador.dto;

public class ReporteKilometrosDTO {
    private Long monopatinId;
    private double kilometrosRecorridos;
    private double tiempoTotal;

    public ReporteKilometrosDTO(Long monopatinId, double kilometrosRecorridos, double tiempoTotal) {
        this.monopatinId = monopatinId;
        this.kilometrosRecorridos = kilometrosRecorridos;
        this.tiempoTotal = tiempoTotal;
    }

    // Getters y setters
    public Long getMonopatinId() {
        return monopatinId;
    }

    public void setMonopatinId(Long monopatinId) {
        this.monopatinId = monopatinId;
    }

    public double getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public double gettiempoTotal() {
        return tiempoTotal;
    }

    public void settiempoTotal(double tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }
}
