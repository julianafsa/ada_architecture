package br.com.ada.stickers.service;

import java.util.Optional;

public interface AlbumService {
    Optional<String> findUserIdByAlbumId(String albumId);
}
