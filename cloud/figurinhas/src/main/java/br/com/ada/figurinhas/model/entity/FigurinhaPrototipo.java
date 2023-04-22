package br.com.ada.stickers.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "sticker_template")
@NoArgsConstructor
@AllArgsConstructor
public class StickerTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "albumTemplateId", nullable = false)
    private String albumTemplateId;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "rarity", nullable = false)
    private Integer rarity;

    @Column(name = "sticker_price", nullable = false)
    private BigDecimal stickerPrice;

    @OneToMany(mappedBy = "stickerTemplate")
    private List<Sticker> stickers;
}
