package br.com.ada.stickers.model.dto;

import br.com.ada.stickers.model.entity.Sticker;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StickerToSellCreationDTO {
    @NotNull(message = "Sticker field is missing or blank.")
    private Sticker sticker;

    @NotNull(message = "Price field is missing or blank.")
    private BigDecimal price;
}
