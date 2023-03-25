package br.com.ada.stickers.model.dto;

import br.com.ada.stickers.model.entity.StickerTemplate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerUpdateDTO {
    @NotNull(message = "StickerTemplate field is missing or blank.")
    private StickerTemplate stickerTemplate;

    @NotBlank(message = "AlbumId field is missing or blank.")
    private String albumId;
}
