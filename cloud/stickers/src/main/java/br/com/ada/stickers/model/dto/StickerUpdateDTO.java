package br.com.ada.stickers.model.dto;

import br.com.ada.stickers.model.enumeration.RaritySticker;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StickerUpdateDTO {
    private String image;

    private String description;

    private RaritySticker rarity;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=2)
    private BigDecimal price;
}
