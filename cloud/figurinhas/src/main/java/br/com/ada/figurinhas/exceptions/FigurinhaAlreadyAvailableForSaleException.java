package br.com.ada.figurinhas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Figurinha is available for sale.")
public class FigurinhaAlreadyAvailableForSaleException extends RuntimeException {
    public FigurinhaAlreadyAvailableForSaleException() {
        super("Figurinha is available for sale");
    }
    public FigurinhaAlreadyAvailableForSaleException(String errorMessage) {
        super(errorMessage);
    }
}
