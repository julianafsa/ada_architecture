package br.com.ada.figurinhas.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FigurinhaUpdateDTO {
    @NotNull(message = "FigurinhaPrototipo field is missing or blank.")
    private String figurinhaPrototipoId;

    @NotBlank(message = "AlbumId field is missing or blank.")
    private String albumId;
}
