package br.com.ada.stickers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StickerJournalDTO {
    private String id;

    private String sourceAlbumId;

    private String destinationAlbumId;

    private StickerDTO sticker;

    private LocalDateTime date;

    private BigDecimal price;
}
