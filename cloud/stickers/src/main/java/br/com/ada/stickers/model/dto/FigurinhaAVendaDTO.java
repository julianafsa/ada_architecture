package br.com.ada.stickers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FigurinhaAVendaDTO {
    private String id;

    private FigurinhaDTO sticker;

    private BigDecimal price;
}
