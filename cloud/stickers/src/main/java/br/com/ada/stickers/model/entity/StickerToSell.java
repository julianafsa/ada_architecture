package br.com.ada.stickers.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "sticker-to-sell")
@NoArgsConstructor
@AllArgsConstructor
public class StickerToSell {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "stickerIdFk", referencedColumnName = "id", nullable = false)
    private Sticker sticker;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
