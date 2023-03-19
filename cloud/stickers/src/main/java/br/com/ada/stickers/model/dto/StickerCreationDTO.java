package br.com.ada.stickers.model.dto;

import br.com.ada.stickers.model.enumeration.RaritySticker;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StickerCreationDTO {
    @NotBlank(message = "Image field is missing or blank.")
    private String image;

    @NotBlank(message = "Description field is missing or blank.")
    private String description;

    @NotNull(message = "Rarirty field is missing or blank.")
    private RaritySticker rarity;

    @NotBlank(message = "Price field is missing or blank.")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=2)
    private BigDecimal price;
}
