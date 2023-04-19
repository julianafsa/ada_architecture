package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.entity.PrototipoDeAlbum;
import br.com.ada.albuns.model.entity.TransacaoAlbum;
import br.com.ada.albuns.repository.TransacaoAlbumRepository;
import br.com.ada.albuns.repository.PrototipoDeAlbumRepository;
import br.com.ada.albuns.service.AlbumService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Using the Design Pattern: Decorator Pattern
// The AlbumServiceWithJournal is an implementation that creates the album and logs the
// transaction to the album journal. The other methods are simply forwarded to the base
// implementation with no further action.
// SOLID: the motivation here is the Single Responsibility principle of the SOLID, as
// each service is responsible for a single responsibility: one creates the album,
// the other logs the album creation to the album journal.
@Qualifier("AlbumServiceWithJournal")
@Service
@Slf4j
public class AlbumServiceWithTransacaoImpl implements AlbumService {

    private final AlbumService albumService;
    private final TransacaoAlbumRepository repository;
    private final PrototipoDeAlbumRepository prototipoDeAlbumRepository;

    public AlbumServiceWithTransacaoImpl(AlbumService albumService, TransacaoAlbumRepository repository, PrototipoDeAlbumRepository prototipoDeAlbumRepository) {
        this.albumService = albumService;
        this.repository = repository;
        this.prototipoDeAlbumRepository = prototipoDeAlbumRepository;
    }

    @Override
    public List<AlbumDTO> findAll() {
        return albumService.findAll();
    }

    @Override
    public AlbumDTO findById(String id) {
        return albumService.findById(id);
    }

    @Override
    public Optional<Album> findByAlbumId(String id) {
        return Optional.empty();
    }

    @Override
    public AlbumDTO create(AlbumDTO entity) {
        // Executes the base implementation behavior
        AlbumDTO newAlbum = albumService.create(entity);

        // And then add the behavior to log the transaction to the album journal
        PrototipoDeAlbum prototipoDeAlbum = prototipoDeAlbumRepository.findById(newAlbum.getAlbumTemplateId()).orElseThrow(() -> new EntityNotFoundException());

        TransacaoAlbum transacaoAlbum = TransacaoAlbum.builder()
                .userId(newAlbum.getUserId())
                .albumId(newAlbum.getId())
                .albumTemplateId(prototipoDeAlbum.getId())
                .albumTemplateName(prototipoDeAlbum.getName())
                .price(prototipoDeAlbum.getPrice())
                .dateTime(LocalDateTime.now())
                .build();

        repository.save(transacaoAlbum);

        // Returns the result of the base implementation
        return newAlbum;
    }

    @Override
    public AlbumDTO findDefaultAlbum(String albumTemplateId) {
        return albumService.findDefaultAlbum(albumTemplateId);
    }

    @Override
    public Optional<String> findUserIdByAlbumId(String albumId) {
        return repository.findUserIdByAlbumId(albumId);
    }

    @Override
    @Transactional
    public void delete(String albumId) {
        Optional<TransacaoAlbum> albumJournal = repository.findByAlbumId(albumId);
        Optional<Album> album = albumService.findByAlbumId(albumId);
        if(album.isPresent() && albumJournal.isPresent() ) {

            log.info("Deleting album id: " + album.get().getId());
            albumService.delete(albumId);

            log.info("Deleting transaction album id: " + albumJournal.get().getId());
            repository.delete(albumJournal.get());
        }else {
            throw new RuntimeException("Error to find album");
        }
    }
}