package br.com.ada.albuns.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumTemplateDTO {

  private Long id;

  private String name;

  private String cover;

  private Long numStickers;

  private BigDecimal price;
  
}
