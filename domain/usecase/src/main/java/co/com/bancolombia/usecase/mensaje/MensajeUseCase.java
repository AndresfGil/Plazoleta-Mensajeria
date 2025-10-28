package co.com.bancolombia.usecase.mensaje;

import co.com.bancolombia.model.mensaje.Mensaje;
import co.com.bancolombia.model.mensaje.gateways.MensajeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MensajeUseCase {
    
    private final MensajeRepository mensajeRepository;
    
    public void enviarMensaje(Mensaje mensaje) {
        mensajeRepository.enviarMensaje(mensaje);
    }
}
