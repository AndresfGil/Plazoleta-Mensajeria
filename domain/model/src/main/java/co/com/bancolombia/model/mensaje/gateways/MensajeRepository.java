package co.com.bancolombia.model.mensaje.gateways;

import co.com.bancolombia.model.mensaje.Mensaje;

public interface MensajeRepository {
    void enviarMensaje(Mensaje mensaje);
}
