package br.com.ada.stickers.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StickerBuyFromAlbumDTO {
    @NotBlank(message = "StickerId field is missing or blank.")
    private String stickerId;

    @NotBlank(message = "DestinationAlbumId field is missing or blank.")
    private String destinationAlbumId;
}
