package br.com.ada.albuns.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "album_journal")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumJournal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "usuarioId", nullable = false)
  private String usuarioId;

  @Column(name = "albumId", nullable = false)
  private String albumId;
  
  @Column(name = "albumPrototipoId", nullable = false)
  private String albumPrototipoId;
  
  @Column(name = "albumPrototipoName", nullable = false)
  private String albumPrototipoName;
  
  @Column(name = "dateTime", nullable = false)
  private LocalDateTime dateTime;
  
  @Column(name = "price", nullable = false)
  private BigDecimal price;
}
