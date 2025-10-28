package co.com.bancolombia.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MensajeRequest {
    
    @NotBlank(message = "El teléfono es obligatorio")
    @NotNull(message = "El teléfono no puede ser nulo")
    private String telefono;
    
    @NotBlank(message = "El mensaje es obligatorio")
    @NotNull(message = "El mensaje no puede ser nulo")
    private String mensaje;
}
