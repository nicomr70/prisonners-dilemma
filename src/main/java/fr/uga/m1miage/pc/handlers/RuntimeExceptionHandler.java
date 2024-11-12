package fr.uga.m1miage.pc.handlers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleBadRequest(HttpServletRequest httpServletRequest, Exception e){
        RuntimeException exception = (RuntimeException) e;
        log.error("Erreur", exception);
        return ResponseEntity.status(409).body(exception.getMessage());
    }
}
