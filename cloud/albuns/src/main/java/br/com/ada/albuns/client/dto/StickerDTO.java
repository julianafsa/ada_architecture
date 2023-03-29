package br.com.ada.albuns.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StickerDTO {
    private String id;

    private StickerTemplateDTO stickerTemplate;

    private String albumId;
}
