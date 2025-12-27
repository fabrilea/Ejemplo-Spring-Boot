package micro.example.gateway.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import micro.example.gateway.service.UserService;
import micro.example.gateway.service.dto.user.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> saveUser( @RequestBody @Valid UserDTO userDTO) {
        final var id = userService.saveUser( userDTO );
        return new ResponseEntity<>( id, HttpStatus.CREATED );
    }

    //Lógica del Método
    //
    //    userService.saveUser(userDTO):
    //        Llama al servicio UserService para guardar el usuario representado por el objeto UserDTO.
    //        Devuelve el identificador (id) del usuario recién creado.
    //
    //    ResponseEntity:
    //        Envuelve la respuesta HTTP que el controlador devolverá al cliente.
    //        En este caso:
    //            Cuerpo de la respuesta: id del usuario creado.
    //            Código de estado HTTP: 201 Created (indica que se creó un recurso exitosamente).
}
