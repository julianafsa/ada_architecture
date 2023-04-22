package br.com.ada.stickers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStickerMessage {
    private String albumId;
    private String albumTemplateId;
    private String defaultAlbumId;
}

