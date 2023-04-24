package br.com.ada.figurinhas.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FigurinhaPrototipoDTO {

    private String id;

    private String albumPrototipoId;

    private Integer number;

    private String description;

    private String image;

    private Integer raridade;

    private BigDecimal figurinhaPrice;

}
