package br.com.ada.figurinhas.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FigurinhaBuyPacoteDTO {
    @NotBlank(message = "AlbumId field is missing or blank.")
    private String albumId;

    @NotBlank(message = "DestinationAlbumId field is missing or blank.")
    private String destinationAlbumId;
}
