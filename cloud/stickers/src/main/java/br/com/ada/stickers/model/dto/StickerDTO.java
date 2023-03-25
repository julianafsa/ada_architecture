package br.com.ada.stickers.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerDTO {
    private String id;

    private StickerTemplateDTO stickerTemplate;

    private String albumId;
}
