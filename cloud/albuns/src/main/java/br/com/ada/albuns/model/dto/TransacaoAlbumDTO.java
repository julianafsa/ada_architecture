package br.com.ada.albuns.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoAlbumDTO {

  private Long id;
  private String userId;
  private String albumId;
  private String albumTemplateId;
  private String albumTemplateName;
  private LocalDateTime dateTime;
  private BigDecimal price;
}
