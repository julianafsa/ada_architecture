package br.com.ada.albuns.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FigurinhaCreationDTO {
    @NotNull(message = "FigurinhaPrototipo field is missing or blank.")
    private String figurinhaPrototipoId;

    @NotBlank(message = "AlbumId field is missing or blank.")
    private String albumId;
}
