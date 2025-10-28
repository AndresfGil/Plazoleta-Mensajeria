package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.MensajeRequest;
import co.com.bancolombia.api.dto.MensajeResponse;
import co.com.bancolombia.usecase.mensaje.MensajeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ApiRestTest {

    @Mock
    private MensajeUseCase mensajeUseCase;

    @InjectMocks
    private ApiRest apiRest;

    private MensajeRequest mensajeRequest;

    @BeforeEach
    void setUp() {
        mensajeRequest = MensajeRequest.builder()
                .telefono("+573001234567")
                .mensaje("Tu pedido está listo. Tu PIN es 8456.")
                .build();
    }

    @Test
    void enviarMensaje_Success() {
        // Arrange
        doNothing().when(mensajeUseCase).enviarMensaje(any());

        // Act
        ResponseEntity<MensajeResponse> response = apiRest.enviarMensaje(mensajeRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().getStatus());
        assertEquals("Mensaje enviado exitosamente", response.getBody().getMessage());
    }

    @Test
    void enviarMensaje_Error() {
        // Arrange
        doThrow(new RuntimeException("Error de Twilio")).when(mensajeUseCase).enviarMensaje(any());

        // Act
        ResponseEntity<MensajeResponse> response = apiRest.enviarMensaje(mensajeRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error", response.getBody().getStatus());
        assertEquals("Error enviando mensaje: Error de Twilio", response.getBody().getMessage());
    }
}
