package br.com.ada.stickers.model.dto;

import br.com.ada.stickers.model.enumeration.RaritySticker;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StickerDTO {
    private Long id;

    private String image;

    private String description;

    private RaritySticker rarity;

    private BigDecimal price;
}
