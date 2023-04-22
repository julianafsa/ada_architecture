package br.com.ada.albuns.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {

  private String id;

  private String usuarioId;

  private String albumPrototipoId;
}
