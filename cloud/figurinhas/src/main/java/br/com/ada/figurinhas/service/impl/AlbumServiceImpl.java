package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.client.AlbumClient;
import br.com.ada.figurinhas.service.AlbumService;
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
    public Optional<String> findUsuarioIdByAlbumId(String albumId) {
        final ResponseEntity<String> response = albumClient.findUsuarioIdByAlbumId(albumId);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Error retrieving usuario id from album id: {}", response.getStatusCode());
            return Optional.empty();
        }
        final String usuarioId = response.getBody();
        log.info("Getting usuario id from albumId = {}. Usuario id is {}", albumId, usuarioId);
        return Optional.of(usuarioId);
    }
}
