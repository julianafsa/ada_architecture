package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.StickerJournalCreationDTO;
import br.com.ada.stickers.model.dto.StickerJournalDTO;
import br.com.ada.stickers.service.StickerJournalService;
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
@RequestMapping(value = "/sticker/journal")
public class StickerJournalController {

    protected final StickerJournalService service;
    public StickerJournalController(final StickerJournalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StickerJournalDTO>> findAll() {
        final List<StickerJournalDTO> response = service.findAll();
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerJournalDTO> findById(@PathVariable("id") String id) {
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
    public ResponseEntity<StickerJournalDTO> create(@RequestBody @Valid StickerJournalCreationDTO creationDTO) {
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

}
