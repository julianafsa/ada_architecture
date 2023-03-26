package br.com.ada.albuns.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StickerCreationDTO {
    @NotNull(message = "StickerTemplate field is missing or blank.")
    private String stickerTemplateId;

    @NotBlank(message = "AlbumId field is missing or blank.")
    private String albumId;
}
