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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "album_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumTemplate {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "cover", nullable = false)
  private String cover;

  @Column(name = "numStickers", nullable = false, unique = true)
  private Long numStickers;

  @Column(name = "albumPrice", nullable = false, unique = true)
  private BigDecimal price;

  @Column(name = "launchDate", nullable = false)
  private LocalDateTime launchDate;

  @Column(name = "expirationDate", nullable = false)
  private LocalDateTime expirationDate;
  
}
