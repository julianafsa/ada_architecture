package br.com.ada.stickers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StickerTemplateDTO {

    private String id;

    private String albumTemplateId;

    private Integer number;

    private String description;

    private String image;

    private Integer rarity;

    private BigDecimal stickerPrice;

}
