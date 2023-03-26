package br.com.ada.albuns.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerUpdateDTO {
    @NotNull(message = "StickerTemplate field is missing or blank.")
    private String stickerTemplateId;

    @NotBlank(message = "AlbumId field is missing or blank.")
    private String albumId;
}
