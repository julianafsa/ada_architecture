package br.com.ada.stickers.model.dto;

import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Rarirty field is missing or blank.")
    @Pattern(regexp="^[1-4]{1}")
    private String rarity;

    @NotNull(message = "Price field is missing or blank.")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=2)
    private BigDecimal price;
}
