package br.com.vendas.rest.controller;

import br.com.vendas.exception.RegraNegocioException;
import br.com.vendas.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    // exception handler são metodos
    // que quando algum erro é lnçado eles
    // capturam e fazem um tratamento em cima disso

    //vai lidar com a regraNegocioException
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
        String exMessage = ex.getMessage();

        return new ApiErrors(exMessage);
    }


}
