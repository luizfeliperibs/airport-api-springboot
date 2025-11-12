package airport.infra;

import airport.exceptions.AeroportoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class TratadorDeErros extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AeroportoNaoEncontradoException.class)
    public ResponseEntity<Object> handleAeroportoNaoEncontrado(AeroportoNaoEncontradoException ex) {

        // Cria um corpo de erro em JSON simples
        Map<String, String> body = Map.of(
                "timestamp", java.time.ZonedDateTime.now().toString(),
                "status", "404",
                "error", "Not Found",
                "message", ex.getMessage()
        );

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}