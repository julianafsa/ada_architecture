package br.com.ada.albuns.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FigurinhaDTO {
    private String id;

    private FigurinhaPrototipoDTO figurinhaPrototipo;

    private String albumId;
}
