package br.com.ada.albuns.service.impl;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.model.entity.Album;
import br.com.ada.albuns.model.entity.AlbumJournal;
import br.com.ada.albuns.model.entity.AlbumPrototipo;
import br.com.ada.albuns.repository.AlbumJournalRepository;
import br.com.ada.albuns.repository.AlbumPrototipoRepository;
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
public class AlbumServiceWithJournalImpl implements AlbumService {

    private final AlbumService albumService;
    private final AlbumJournalRepository repository;
    private final AlbumPrototipoRepository albumPrototipoRepository;

    public AlbumServiceWithJournalImpl(AlbumService albumService, AlbumJournalRepository repository, AlbumPrototipoRepository albumPrototipoRepository) {
        this.albumService = albumService;
        this.repository = repository;
        this.albumPrototipoRepository = albumPrototipoRepository;
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
        AlbumPrototipo albumPrototipo = albumPrototipoRepository.findById(newAlbum.getAlbumPrototipoId()).orElseThrow(() -> new EntityNotFoundException());

        AlbumJournal albumJournal = AlbumJournal.builder()
                .usuarioId(newAlbum.getUsuarioId())
                .albumId(newAlbum.getId())
                .albumPrototipoId(albumPrototipo.getId())
                .albumPrototipoName(albumPrototipo.getName())
                .price(albumPrototipo.getPrice())
                .dateTime(LocalDateTime.now())
                .build();

        repository.save(albumJournal);

        // Returns the result of the base implementation
        return newAlbum;
    }

    @Override
    public AlbumDTO findPadraoAlbum(String albumPrototipoId) {
        return albumService.findPadraoAlbum(albumPrototipoId);
    }

    @Override
    public Optional<String> findUsuarioIdByAlbumId(String albumId) {
        return repository.findUsuarioIdByAlbumId(albumId);
    }

    @Override
    @Transactional
    public void delete(String albumId) {
        Optional<AlbumJournal> albumJournal = repository.findByAlbumId(albumId);
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