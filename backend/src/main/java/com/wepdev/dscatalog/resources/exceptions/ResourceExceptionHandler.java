package com.wepdev.dscatalog.resources.exceptions;

import com.wepdev.dscatalog.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice // Intercepta as excessoes que ocorrem na camada de resources e trata as excessoes
public class ResourceExceptionHandler {


    @ExceptionHandler(EntidadeNaoEncontradaException.class) // Esse e o tipo de excessao que tem que ser interceptada por esse metodo
    public ResponseEntity<ErroPadrao> entidadeNaoEncontrada(EntidadeNaoEncontradaException e, HttpServletRequest request){
         ErroPadrao erro = new ErroPadrao();
         erro.setTimestamp(Instant.now());
         erro.setStatus(HttpStatus.NOT_FOUND.value());
         erro.setError("Recurso não encontrado");
         erro.setMessage(e.getMessage());
         erro.setPath(request.getRequestURI());

         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
}
