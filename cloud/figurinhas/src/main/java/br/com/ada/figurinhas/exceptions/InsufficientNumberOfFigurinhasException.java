package br.com.ada.stickers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Insufficient number of stickers to assemble the pack.")
public class InsufficientNumberOfStickersException extends RuntimeException {
    public InsufficientNumberOfStickersException() {
        super("Insufficient number of stickers to assemble the pack");
    }
}
