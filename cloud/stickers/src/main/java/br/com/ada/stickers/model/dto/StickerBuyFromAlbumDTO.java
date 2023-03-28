package br.com.ada.stickers.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerBuyFromAlbumDTO {
    @NotBlank(message = "StickerId field is missing or blank.")
    private String stickerId;

    @NotBlank(message = "DestinationAlbumId field is missing or blank.")
    private String destinationAlbumId;
}
