package br.com.ada.stickers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StickerToSellDTO {
    private String id;

    private StickerDTO sticker;

    private BigDecimal price;
}
