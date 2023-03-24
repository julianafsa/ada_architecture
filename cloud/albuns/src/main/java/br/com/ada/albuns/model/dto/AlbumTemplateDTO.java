package br.com.ada.albuns.model.dto;

import java.math.BigDecimal;
import java.sql.String;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumTemplateDTO {

  private Integer id;

  private String name;

  private String cover;

  private Integer numStickers;

  private BigDecimal price;
  
}
