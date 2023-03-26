package br.com.ada.stickers.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StickerToSellDTO {
    private String id;

    private StickerDTO sticker;

    private BigDecimal price;
}
