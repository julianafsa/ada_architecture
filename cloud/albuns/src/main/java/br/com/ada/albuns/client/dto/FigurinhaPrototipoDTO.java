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
public class FigurinhaPrototipoDTO {

    private String id;

    private String albumPrototipoId;

    private Integer number;

    private String description;

    private String image;

    private Integer raridade;

    private BigDecimal figurinhaPrice;

}
