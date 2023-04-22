package br.com.ada.figurinhas.model.dto;

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

    private FigurinhaPrototipoDTO figurinhaPrototipo;

    private String albumId;
}
