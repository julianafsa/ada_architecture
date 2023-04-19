package br.com.ada.albuns.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrototipoDeFigurinhaDTO {

    private String id;

    private String albumTemplateId;

    private Integer number;

    private String description;

    private String image;

    private Integer rarity;

    private BigDecimal stickerPrice;

}
