package br.com.ada.albuns.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumTemplateDTO {

  private String id;

  private String name;

  private String cover;

  private Long numStickers;

  private BigDecimal price;


  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime launchDate;

  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime expirationDate;


}
