package br.com.ada.figurinhas.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "figurinha_prototipo")
@NoArgsConstructor
@AllArgsConstructor
public class FigurinhaPrototipo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "albumPrototipoId", nullable = false)
    private String albumPrototipoId;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "raridade", nullable = false)
    private Integer raridade;

    @Column(name = "figurinha_price", nullable = false)
    private BigDecimal figurinhaPrice;

    @OneToMany(mappedBy = "figurinhaPrototipo")
    private List<Figurinha> figurinhas;
}
