package br.com.ada.albuns.model.dto;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateFigurinhaMessage {
    private String albumId;
    private String albumTemplateId;
    private String defaultAlbumId;
}

