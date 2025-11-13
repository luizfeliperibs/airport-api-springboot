package airport.infra;

import airport.exceptions.AeroportoNaoEncontradoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> body = Map.of(
                "status", status.value(), // 400
                "error", "Bad Request",
                "message", "Dados de entrada inválidos.",
                "validation_errors", errors // Mapa com os erros detalhados
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}