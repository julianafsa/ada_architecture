package br.com.ada.albuns.model.entity;

import java.math.BigDecimal;
import java.sql.String;

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
@Table(name = "template")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumTemplate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "cover", nullable = false)
  private String cover;

  @Column(name = "numStickers", nullable = false, unique = true)
  private Integer numStickers;

  @Column(name = "price", nullable = false, unique = true)
  private BigDecimal price;
  
}
