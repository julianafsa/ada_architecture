package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.client.AlbumClient;
import br.com.ada.stickers.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AlbumServiceImpl implements AlbumService {

    private AlbumClient albumClient;
    public AlbumServiceImpl(AlbumClient albumClient) {
        this.albumClient = albumClient;
    }
    @Override
    public Optional<String> findUserIdByAlbumId(String albumId) {
        final ResponseEntity<String> response = albumClient.findUserIdByAlbumId(albumId);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Error retrieving user id from album id: {}", response.getStatusCode());
            return Optional.empty();
        }
        final String userId = response.getBody();
        log.info("Getting user id from albumId = {}. User id is {}", albumId, userId);
        return Optional.of(userId);
    }
}
