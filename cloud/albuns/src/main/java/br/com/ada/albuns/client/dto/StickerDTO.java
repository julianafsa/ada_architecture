package br.com.ada.albuns.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerDTO {
    private String id;

    private StickerTemplateDTO stickerTemplate;

    private String albumId;
}
