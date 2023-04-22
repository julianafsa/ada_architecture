package br.com.ada.stickers.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Entity
@Table(name = "sticker")
@NoArgsConstructor
@AllArgsConstructor
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @JoinColumn(name = "stickerTemplateIdFk", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private StickerTemplate stickerTemplate;

    @Column(name = "albumId", nullable = false)
    private String albumId;

    @OneToMany(mappedBy = "sticker")
    private List<StickerJournal> stickJournal;

    @OneToOne(mappedBy = "sticker")
    private StickerToSell stickerToSell;
}
