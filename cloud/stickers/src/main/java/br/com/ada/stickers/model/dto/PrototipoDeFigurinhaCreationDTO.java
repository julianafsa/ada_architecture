package br.com.ada.stickers.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PrototipoDeFigurinhaCreationDTO {
    @NotBlank(message = "albumTemplateUuid field is missing or blank.")
    private String albumTemplateId;

    @NotNull(message = "Number field is missing or blank.")
    private Integer number;

    @NotBlank(message = "Description field is missing or blank.")
    private String description;

    @NotBlank(message = "Image field is missing or blank.")
    private String image;

    @NotNull(message = "Rarirty field is missing or blank.")
    //@Pattern(regexp="[1-4]")
    private Integer rarity;

    @NotNull(message = "Price field is missing or blank.")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=2)
    private BigDecimal stickerPrice;
}
