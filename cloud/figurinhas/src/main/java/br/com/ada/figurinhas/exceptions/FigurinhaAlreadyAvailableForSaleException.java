package br.com.ada.stickers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Sticker is available for sale.")
public class StickerAlreadyAvailableForSaleException extends RuntimeException {
    public StickerAlreadyAvailableForSaleException() {
        super("Sticker is available for sale");
    }
    public StickerAlreadyAvailableForSaleException(String errorMessage) {
        super(errorMessage);
    }
}
