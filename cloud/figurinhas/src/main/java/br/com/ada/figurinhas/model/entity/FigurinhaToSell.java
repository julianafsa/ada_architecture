package br.com.ada.figurinhas.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "figurinha-to-sell")
@NoArgsConstructor
@AllArgsConstructor
public class FigurinhaToSell {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "figurinhaIdFk", referencedColumnName = "id", nullable = false)
    private Figurinha figurinha;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
