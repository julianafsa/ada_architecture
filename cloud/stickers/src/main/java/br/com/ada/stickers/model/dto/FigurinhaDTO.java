package br.com.ada.stickers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FigurinhaDTO {
    private String id;

    private PrototipoDeFigurinhaDTO stickerTemplate;

    private String albumId;
}
