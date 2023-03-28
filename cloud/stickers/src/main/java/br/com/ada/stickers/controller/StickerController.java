package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.service.StickerService;
import br.com.ada.stickers.service.StickerServiceWithJournal;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/sticker")
public class StickerController {

    private final StickerService service;
    private final StickerMapper mapper;
    private final StickerServiceWithJournal stickerServiceWithJournal;

    public StickerController(final StickerService service,
                             final StickerMapper mapper,
                             final StickerServiceWithJournal stickerServiceWithJournal) {
        this.service = service;
        this.mapper = mapper;
        this.stickerServiceWithJournal = stickerServiceWithJournal;
    }

    @GetMapping
    public ResponseEntity<List<StickerDTO>> findAll() {
        final List<StickerDTO> response = service.findAll();
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerDTO> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<StickerDTO> create(@RequestBody @Valid StickerCreationDTO creationDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.create(creationDTO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StickerDTO> edit(@PathVariable("id") String id,
                                           @RequestBody @Valid StickerUpdateDTO updateDTO) {
        try {
            return ResponseEntity.ok(mapper.parseDTO(service.edit(id, updateDTO)));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/buy/pack")
    public ResponseEntity<List<StickerDTO>> buyStickerPack(@RequestBody @Valid StickerBuyPackDTO stickerBuyPackDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.parseListDTO(stickerServiceWithJournal.buyStickerPack(stickerBuyPackDTO)));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<StickerDTO> buyStickerFromAlbum(@RequestBody @Valid StickerBuyFromAlbumDTO stickerBuyFromAlbumDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.parseDTO(stickerServiceWithJournal.buyStickerFromAlbum(stickerBuyFromAlbumDTO)));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
