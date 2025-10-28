package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.MensajeRequest;
import co.com.bancolombia.api.dto.MensajeResponse;
import co.com.bancolombia.model.mensaje.Mensaje;
import co.com.bancolombia.usecase.mensaje.MensajeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final MensajeUseCase mensajeUseCase;

    @PostMapping(path = "/mensaje/enviar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeResponse> enviarMensaje(@Valid @RequestBody MensajeRequest request) {
        try {
            Mensaje mensaje = Mensaje.builder()
                    .telefono(request.getTelefono())
                    .mensaje(request.getMensaje())
                    .build();
            
            mensajeUseCase.enviarMensaje(mensaje);
            
            MensajeResponse response = MensajeResponse.builder()
                    .status("success")
                    .message("Mensaje enviado exitosamente")
                    .build();
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            MensajeResponse response = MensajeResponse.builder()
                    .status("error")
                    .message("Error enviando mensaje: " + e.getMessage())
                    .build();
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
