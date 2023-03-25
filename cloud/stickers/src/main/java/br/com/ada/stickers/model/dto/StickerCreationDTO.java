package br.com.ada.stickers.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerCreationDTO {
    @NotBlank(message = "StickerTemplateId field is missing or blank.")
    private String stickerTemplateId;

    @NotBlank(message = "AlbumId field is missing or blank.")
    private String albumId;
}
