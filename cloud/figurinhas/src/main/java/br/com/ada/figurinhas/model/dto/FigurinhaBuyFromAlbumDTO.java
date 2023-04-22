package br.com.ada.figurinhas.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FigurinhaBuyFromAlbumDTO {
    @NotBlank(message = "FigurinhaId field is missing or blank.")
    private String figurinhaId;

    @NotBlank(message = "DestinationAlbumId field is missing or blank.")
    private String destinationAlbumId;
}
