package co.com.bancolombia.consumer;

import co.com.bancolombia.model.mensaje.Mensaje;
import co.com.bancolombia.model.mensaje.gateways.MensajeRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RestConsumer implements MensajeRepository {
    
    private final String accountSid;
    private final String authToken;
    private final String fromNumber;

    public RestConsumer(@Value("${twilio.account-sid}") String accountSid,
                       @Value("${twilio.auth-token}") String authToken,
                       @Value("${twilio.from-number}") String fromNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromNumber = fromNumber;
        
        Twilio.init(accountSid, authToken);
    }

    @Override
    @CircuitBreaker(name = "twilioSms", fallbackMethod = "enviarMensajeFallback")
    public void enviarMensaje(Mensaje mensaje) {
        try {
            Message.creator(
                new PhoneNumber(mensaje.getTelefono()),
                new PhoneNumber(fromNumber),
                mensaje.getMensaje()
            ).create();
        } catch (Exception e) {
            throw new RuntimeException("Error enviando mensaje SMS: " + e.getMessage(), e);
        }
    }

    public void enviarMensajeFallback(Mensaje mensaje, Exception ex) {
        System.err.println("Fallback: No se pudo enviar mensaje a " + mensaje.getTelefono() +
                          ". Error: " + ex.getMessage());
    }
}
