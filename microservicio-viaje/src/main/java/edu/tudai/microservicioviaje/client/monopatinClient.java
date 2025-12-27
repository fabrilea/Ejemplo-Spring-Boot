package edu.tudai.microservicioviaje.client;


import edu.tudai.microservicioviaje.dto.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8002/api/monopatin")
public interface monopatinClient {

    @GetMapping("/{id}")
    MonopatinDTO getMonopatinById(@PathVariable("id") Long monopatinId);
}

