package br.com.ada.stickers.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StickerDTO {
    private Long id;

    private String image;

    private String description;

    private String rarity;

    private BigDecimal price;
}
