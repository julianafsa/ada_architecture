package br.com.ada.stickers.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "sticker_journal")
@NoArgsConstructor
@AllArgsConstructor
public class StickerJournal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "sourceAlbumId", nullable = false)
    private String sourceAlbumId;

    @Column(name = "destinationAlbumId", nullable = false)
    private String destinationAlbumId;

    @JoinColumn(name = "stickerIdFk", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Sticker sticker;

    @Column(name = "date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime date;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
