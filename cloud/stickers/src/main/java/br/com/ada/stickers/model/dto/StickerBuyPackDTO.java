package br.com.ada.stickers.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerBuyPackDTO {
    @NotBlank(message = "AlbumId field is missing or blank.")
    private String albumId;

    @NotBlank(message = "DestinationAlbumId field is missing or blank.")
    private String destinationAlbumId;
}
