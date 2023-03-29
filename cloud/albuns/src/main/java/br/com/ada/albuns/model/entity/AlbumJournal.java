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

  @Column(name = "userId", nullable = false)
  private String userId;

  @Column(name = "albumId", nullable = false)
  private String albumId;
  
  @Column(name = "albumTemplateId", nullable = false)
  private String albumTemplateId;
  
  @Column(name = "albumTemplateName", nullable = false)
  private String albumTemplateName;
  
  @Column(name = "dateTime", nullable = false)
  private LocalDateTime dateTime;
  
  @Column(name = "price", nullable = false)
  private BigDecimal price;
}
