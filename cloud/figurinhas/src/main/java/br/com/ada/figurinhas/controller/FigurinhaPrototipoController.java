package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.StickerTemplateCreationDTO;
import br.com.ada.stickers.model.dto.StickerTemplateDTO;
import br.com.ada.stickers.model.dto.StickerTemplateUpdateDTO;
import br.com.ada.stickers.service.StickerTemplateService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/sticker/template")
public class StickerTemplateController {

    private final StickerTemplateService service;
    public StickerTemplateController(final StickerTemplateService service) {
        this.service = service;
    
    }
    @GetMapping
    public ResponseEntity<List<StickerTemplateDTO>> findAll(@RequestParam("albumTemplateId") Optional<String> opAlbumTemplateId) {
        final List<StickerTemplateDTO> response = service.findAll(opAlbumTemplateId);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerTemplateDTO> findById(@PathVariable("id") String id) {
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
    public ResponseEntity<StickerTemplateDTO> create(@RequestBody @Valid StickerTemplateCreationDTO creationDTO) {
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
    public ResponseEntity<StickerTemplateDTO> edit(@PathVariable("id") String id,
                                           @RequestBody @Valid StickerTemplateUpdateDTO updateDTO) {
        try {
            return ResponseEntity.ok(service.edit(id, updateDTO));
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

}
