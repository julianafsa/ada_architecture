package br.com.ada.stickers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Sticker not available for sale.")
public class StickerNotAvailableForSale extends RuntimeException {
    public StickerNotAvailableForSale() {
        super("Sticker not available for sale");
    }
}
