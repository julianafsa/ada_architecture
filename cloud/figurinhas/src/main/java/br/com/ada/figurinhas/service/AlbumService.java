package br.com.ada.figurinhas.service;

import java.util.Optional;

public interface AlbumService {
    Optional<String> findUsuarioIdByAlbumId(String albumId);
}
