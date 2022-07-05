package br.com.vr.autorizador.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationExceptionHandler
{
  @ResponseStatus(value = HttpStatus.BAD_REQUEST,
          reason = "Requisição inválida")
  @ExceptionHandler(IllegalArgumentException.class)
  public void handleException(IllegalArgumentException e) {
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST,
          reason = "Requisição inválida")
  @ExceptionHandler(NumberFormatException.class)
  public void handleException(NumberFormatException e) {
  }
}
