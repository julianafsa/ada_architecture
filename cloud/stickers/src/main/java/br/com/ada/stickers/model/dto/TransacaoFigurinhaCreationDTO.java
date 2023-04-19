package br.com.ada.stickers.model.dto;

import br.com.ada.stickers.model.entity.Figurinha;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoFigurinhaCreationDTO {
    @NotBlank(message = "SourceAlbumId field is missing or blank.")
    private String sourceAlbumId;

    @NotBlank(message = "DestinationAlbumId field is missing or blank.")
    private String destinationAlbumId;

    @NotNull(message = "Sticker field is missing or blank.")
    private Figurinha sticker;

    @NotNull(message = "Price field is missing or blank.")
    private BigDecimal price;
}