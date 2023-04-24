package br.com.ada.figurinhas.model.dto;

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
public class FigurinhaJournalDTO {
    private String id;

    private String sourceAlbumId;

    private String destinationAlbumId;

    private FigurinhaDTO figurinha;

    private LocalDateTime date;

    private BigDecimal price;
}
