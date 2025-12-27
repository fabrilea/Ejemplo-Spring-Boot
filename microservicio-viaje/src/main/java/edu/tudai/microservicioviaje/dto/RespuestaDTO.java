package edu.tudai.microservicioviaje.dto;

public class RespuestaDTO {

    private Long monopatinId;
    private Long totalViajes;

    public RespuestaDTO() {
        super();
    }

    public RespuestaDTO(Long monopatinId, Long totalViajes) {
        super();
        this.monopatinId = monopatinId;
        this.totalViajes = totalViajes;
    }

    public Long getMonopatinId() {
        return monopatinId;
    }

    public void setMonopatinId(Long monopatinId) {
        this.monopatinId = monopatinId;
    }

    public Long getTotalViajes() {
        return totalViajes;
    }

    public void setTotalViajes(Long totalViajes) {
        this.totalViajes = totalViajes;
    }
}
